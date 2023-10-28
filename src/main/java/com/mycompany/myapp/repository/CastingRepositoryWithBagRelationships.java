package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Casting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CastingRepositoryWithBagRelationships {
    Optional<Casting> fetchBagRelationships(Optional<Casting> casting);

    List<Casting> fetchBagRelationships(List<Casting> castings);

    Page<Casting> fetchBagRelationships(Page<Casting> castings);
}
