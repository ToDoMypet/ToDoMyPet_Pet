package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.AddPetReqDTO;
import com.todomypet.petservice.dto.AdoptPetReqDTO;

public interface PetService {
    String addPet(AddPetReqDTO addPetReqDTO);
    void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO);
}
