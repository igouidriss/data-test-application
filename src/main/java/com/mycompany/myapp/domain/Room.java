package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @Column(name = "screen_height")
    private Integer screenHeight;

    @Column(name = "screen_width")
    private Integer screenWidth;

    @Column(name = "distance")
    private Integer distance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "session", "room" }, allowSetters = true)
    private Set<Period> periods = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "operator", "address", "rooms" }, allowSetters = true)
    private Cinema cinema;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Room id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatsNumber() {
        return this.seatsNumber;
    }

    public Room seatsNumber(Integer seatsNumber) {
        this.setSeatsNumber(seatsNumber);
        return this;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Integer getScreenHeight() {
        return this.screenHeight;
    }

    public Room screenHeight(Integer screenHeight) {
        this.setScreenHeight(screenHeight);
        return this;
    }

    public void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }

    public Integer getScreenWidth() {
        return this.screenWidth;
    }

    public Room screenWidth(Integer screenWidth) {
        this.setScreenWidth(screenWidth);
        return this;
    }

    public void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }

    public Integer getDistance() {
        return this.distance;
    }

    public Room distance(Integer distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<Period> getPeriods() {
        return this.periods;
    }

    public void setPeriods(Set<Period> periods) {
        if (this.periods != null) {
            this.periods.forEach(i -> i.setRoom(null));
        }
        if (periods != null) {
            periods.forEach(i -> i.setRoom(this));
        }
        this.periods = periods;
    }

    public Room periods(Set<Period> periods) {
        this.setPeriods(periods);
        return this;
    }

    public Room addPeriod(Period period) {
        this.periods.add(period);
        period.setRoom(this);
        return this;
    }

    public Room removePeriod(Period period) {
        this.periods.remove(period);
        period.setRoom(null);
        return this;
    }

    public Cinema getCinema() {
        return this.cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Room cinema(Cinema cinema) {
        this.setCinema(cinema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return getId() != null && getId().equals(((Room) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", seatsNumber=" + getSeatsNumber() +
            ", screenHeight=" + getScreenHeight() +
            ", screenWidth=" + getScreenWidth() +
            ", distance=" + getDistance() +
            "}";
    }
}
