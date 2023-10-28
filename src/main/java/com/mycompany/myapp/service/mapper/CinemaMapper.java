package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Cinema;
import com.mycompany.myapp.domain.Operator;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.dto.CinemaDTO;
import com.mycompany.myapp.service.dto.OperatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cinema} and its DTO {@link CinemaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CinemaMapper extends EntityMapper<CinemaDTO, Cinema> {
    @Mapping(target = "operator", source = "operator", qualifiedByName = "operatorId")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    CinemaDTO toDto(Cinema s);

    @Named("operatorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperatorDTO toDtoOperatorId(Operator operator);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
