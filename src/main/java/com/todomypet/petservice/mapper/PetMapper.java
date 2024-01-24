package com.todomypet.petservice.mapper;

import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.dto.pet.GetAvailableStartingPetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {
    GetAvailableStartingPetDTO petToGetAvailableStartingPetDTO(Pet pet);
}
