package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Operator;
import com.mycompany.myapp.service.dto.OperatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operator} and its DTO {@link OperatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperatorMapper extends EntityMapper<OperatorDTO, Operator> {}
