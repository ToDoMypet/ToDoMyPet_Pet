package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.*;
import com.todomypet.petservice.dto.pet.*;

import java.util.List;

public interface PetService {
    void addPet(AddPetReqDTOList addPetReqDTO);
    void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO);
    AdoptedPetResListDTO getAdoptedPetList(String userId);
    GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode);
    PetDetailResDTO getPetDetail(String userId, String seq);
    void renamePet(String userId, RenamePetReqDTO renamePetReqDTO);
    GetPetCollectionListResDTO getPetCollection(String userId);
    List<CommunityPetListResDTO> getCommunityPetList(String userId);
    UpdateExperiencePointResDTO updateExperiencePoint(String userId, UpdateExperiencePointReqDTO updateExperiencePointReqDTO);
    List<GetPetUpgradeChoiceResDTO> getPetUpgradeChoice(String userId, String petId);

    UpgradePetResDTO evolvePet(String userId, UpgradePetReqDTO req);

    GraduatePetResDTO graduatePet(String userId, GraduatePetReqDTO req);

    String getMainPetSeqByUserId(String userId);

    List<GetAvailableStartingPetDTO> getAvailableStartingPet(String userId);

    GetMainPetInfosResDTO getMainPetInfosByUserId(String userId);
}
