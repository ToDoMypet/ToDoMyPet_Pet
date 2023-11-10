package com.todomypet.petservice.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.domain.relationship.Adopt;
import com.todomypet.petservice.dto.*;
import com.todomypet.petservice.exception.CustomException;
import com.todomypet.petservice.exception.ErrorCode;
import com.todomypet.petservice.repository.AdoptRepository;
import com.todomypet.petservice.repository.PetRepository;
import com.todomypet.petservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AdoptRepository adoptRepository;
    private final UserRepository userRepository;


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
    @Transactional
    public void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO) {
        LocalDateTime adoptAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        StringBuilder signatureCode = new StringBuilder();
        Random rnd = new Random();

        while (true) {
            for (int i = 0; i < 2; i++) {
                signatureCode.append((char)(rnd.nextInt(26) + 65));
            }
            for (int i = 0; i < 9; i++) {
                signatureCode.append(rnd.nextInt(10));
            }

            if (adoptRepository.getOneAdoptBySignatureCode(signatureCode.toString()).isEmpty()) {
                break;
            }
        }

        userRepository.increasePetCount(userId);


        adoptRepository.createAdoptBetweenAdoptAndUser(userId, adoptPetReqDTO.getPetId(),
                adoptPetReqDTO.getRename(), adoptAt, UlidCreator.getUlid().toString(), signatureCode.toString());
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
        return AdoptedPetResListDTO.builder().petList(adoptedPetResDTOList).build();
    }

    @Override
    public GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode) {
        List<Adopt> petInfos = adoptRepository.getMyPetInfo(userId, signatureCode);
        List<GetMyPetInfoResDTO> getMyPetInfoResDTOList = new ArrayList<>();
        for (Adopt adopt : petInfos) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());

            GetMyPetInfoResDTO getMyPetInfoResDTO = GetMyPetInfoResDTO.builder()
                    .portraitUrl(pet.getPortraitUrl())
                    .name(adopt.getName())
                    .maxExperience(pet.getMaxExperience())
                    .experience(adopt.getExperience())
                    .grade(pet.getGrade())
                    .graduated(adopt.getGraduated())
                    .build();

            getMyPetInfoResDTOList.add(getMyPetInfoResDTO);
        }
        return GetMyPetInfoResListDTO.builder().build();
    }

    @Override
    @Transactional
    public PetDetailResDTO getPetDetail(String userId, String seq) {
        Adopt adopt = adoptRepository.getAdoptBySeq(userId, seq);
        Pet pet = petRepository.getPetBySeqOfAdopt(seq);

        return PetDetailResDTO.builder()
                .grade(pet.getGrade())
                .name(adopt.getName())
                .type(pet.getType())
                .personality(pet.getPersonality())
                .description(pet.getPersonality())
                .build();
    }

    @Override
    public void renamePet(String userId, RenamePetReqDTO renamePetReqDTO) {
        Adopt adopt = adoptRepository.getOneAdoptByUserIdAndSignatureCode(userId, renamePetReqDTO.getSignatureCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_COLLECT_USER_AND_SIGNATURE_CODE));
        adoptRepository.renamePet(userId, renamePetReqDTO.getSignatureCode(), renamePetReqDTO.getRename());
    }
}
