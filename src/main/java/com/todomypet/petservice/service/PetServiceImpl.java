package com.todomypet.petservice.service;

import com.todomypet.petservice.domain.Pet;
import com.todomypet.petservice.dto.AddPetReqDTO;
import com.todomypet.petservice.repository.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;


    @Transactional
    @Override
    public String addPet(AddPetReqDTO addPetReqDTO) {
        Pet p = Pet.builder().id(addPetReqDTO.getId())
                    .name(addPetReqDTO.getName())
                    .maxExperience(addPetReqDTO.getMaxExperience())
                    .portraitUrl(addPetReqDTO.getPortraitUrl())
                    .describe(addPetReqDTO.getDescribe())
                    .personality(addPetReqDTO.getPersonality())
                    .petCondition(addPetReqDTO.getPetCondition())
                    .type(addPetReqDTO.getType())
                    .grade(addPetReqDTO.getGrade())
                    .build();
        return petRepository.save(p).getId();
    }
}
