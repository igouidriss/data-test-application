package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Session} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SessionDTO implements Serializable {

    private Long id;

    private Integer startHour;

    private PeriodDTO period;

    private MovieDTO movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public PeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PeriodDTO period) {
        this.period = period;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionDTO)) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", startHour=" + getStartHour() +
            ", period=" + getPeriod() +
            ", movie=" + getMovie() +
            "}";
    }
}
