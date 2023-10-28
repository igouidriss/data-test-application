package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Casting;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CastingRepositoryWithBagRelationshipsImpl implements CastingRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Casting> fetchBagRelationships(Optional<Casting> casting) {
        return casting.map(this::fetchActors);
    }

    @Override
    public Page<Casting> fetchBagRelationships(Page<Casting> castings) {
        return new PageImpl<>(fetchBagRelationships(castings.getContent()), castings.getPageable(), castings.getTotalElements());
    }

    @Override
    public List<Casting> fetchBagRelationships(List<Casting> castings) {
        return Optional.of(castings).map(this::fetchActors).orElse(Collections.emptyList());
    }

    Casting fetchActors(Casting result) {
        return entityManager
            .createQuery("select casting from Casting casting left join fetch casting.actors where casting.id = :id", Casting.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Casting> fetchActors(List<Casting> castings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, castings.size()).forEach(index -> order.put(castings.get(index).getId(), index));
        List<Casting> result = entityManager
            .createQuery("select casting from Casting casting left join fetch casting.actors where casting in :castings", Casting.class)
            .setParameter("castings", castings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
