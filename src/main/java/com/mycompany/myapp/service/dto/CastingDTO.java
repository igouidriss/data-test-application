package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Casting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CastingDTO implements Serializable {

    private Long id;

    private MovieDTO movie;

    private Set<ActorDTO> actors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CastingDTO)) {
            return false;
        }

        CastingDTO castingDTO = (CastingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, castingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CastingDTO{" +
            "id=" + getId() +
            ", movie=" + getMovie() +
            ", actors=" + getActors() +
            "}";
    }
}
