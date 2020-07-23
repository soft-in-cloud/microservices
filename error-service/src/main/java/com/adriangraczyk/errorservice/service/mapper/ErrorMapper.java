package com.adriangraczyk.errorservice.service.mapper;


import com.adriangraczyk.errorservice.domain.*;
import com.adriangraczyk.errorservice.domain.Error;
import com.adriangraczyk.errorservice.service.dto.ErrorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Error} and its DTO {@link ErrorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ErrorMapper extends EntityMapper<ErrorDTO, Error> {



    default Error fromId(String id) {
        if (id == null) {
            return null;
        }
        Error error = new Error();
        error.setId(id);
        return error;
    }
}
