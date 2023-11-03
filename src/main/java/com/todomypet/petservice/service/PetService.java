package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.AddPetReqDTO;

public interface PetService {
    String addPet(AddPetReqDTO addPetReqDTO);
}
