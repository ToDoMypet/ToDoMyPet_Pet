package com.todomypet.petservice.mapper;

import com.todomypet.petservice.domain.node.Background;
import com.todomypet.petservice.dto.BackgroundResDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BackgroundMapper {
    BackgroundResDTO backgroundToBackgroundResDTO(Background background);
}
