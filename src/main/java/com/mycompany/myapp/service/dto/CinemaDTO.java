package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Cinema} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CinemaDTO implements Serializable {

    private Long id;

    private String name;

    private OperatorDTO operator;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OperatorDTO getOperator() {
        return operator;
    }

    public void setOperator(OperatorDTO operator) {
        this.operator = operator;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CinemaDTO)) {
            return false;
        }

        CinemaDTO cinemaDTO = (CinemaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cinemaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CinemaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", operator=" + getOperator() +
            ", address=" + getAddress() +
            "}";
    }
}
