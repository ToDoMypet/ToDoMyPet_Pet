package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.*;

public interface PetService {
    String addPet(AddPetReqDTO addPetReqDTO);
    void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO);
    AdoptedPetResListDTO getAdoptedPetList(String userId);
    GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode);
    PetDetailResDTO getPetDetail(String userId, String seq);
    void renamePet(String userId, RenamePetReqDTO renamePetReqDTO);
    GetPetCollectionListResDTO getPetCollection(String userId);
}
