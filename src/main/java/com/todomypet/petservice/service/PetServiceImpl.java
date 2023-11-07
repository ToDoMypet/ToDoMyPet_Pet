package com.todomypet.petservice.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.domain.relationship.Adopt;
import com.todomypet.petservice.dto.AddPetReqDTO;
import com.todomypet.petservice.dto.AdoptPetReqDTO;
import com.todomypet.petservice.dto.AdoptedPetResDTO;
import com.todomypet.petservice.dto.AdoptedPetResListDTO;
import com.todomypet.petservice.repository.AdoptRepository;
import com.todomypet.petservice.repository.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AdoptRepository adoptRepository;


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

    @Override
    public void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO) {
        LocalDateTime adoptAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        adoptRepository.createAdoptBetweenAdoptAndUser(userId, adoptPetReqDTO.getPetId(),
                adoptPetReqDTO.getRename(), adoptAt, UlidCreator.getUlid().toString());
    }

    @Override
    public AdoptedPetResListDTO getAdoptedPetList(String userId) {
        List<Adopt> getAdoptList = adoptRepository.getAdoptList(userId);
        List<AdoptedPetResDTO> adoptedPetResDTOList = new ArrayList<AdoptedPetResDTO>();
        for(Adopt adopt : getAdoptList) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());
            AdoptedPetResDTO adoptedPetResDTO = AdoptedPetResDTO.builder()
                    .name(adopt.getName())
                    .experience(adopt.getExperience())
                    .portraitUrl(pet.getPortraitUrl())
                    .grade(pet.getGrade())
                    .maxExperience(pet.getMaxExperience())
                    .build();
            adoptedPetResDTOList.add(adoptedPetResDTO);
        }
        AdoptedPetResListDTO response = AdoptedPetResListDTO.builder().petList(adoptedPetResDTOList).build();
        return response;
    }
}
