package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.AddPetReqDTO;
import com.todomypet.petservice.dto.AdoptPetReqDTO;
import com.todomypet.petservice.dto.AdoptedPetResDTO;
import com.todomypet.petservice.dto.AdoptedPetResListDTO;

import java.util.List;

public interface PetService {
    String addPet(AddPetReqDTO addPetReqDTO);
    void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO);
    AdoptedPetResListDTO getAdoptedPetList(String userId);
}
