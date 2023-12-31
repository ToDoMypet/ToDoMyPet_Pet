package com.todomypet.petservice.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.domain.node.PetPersonalityType;
import com.todomypet.petservice.domain.node.PetType;
import com.todomypet.petservice.domain.relationship.Adopt;
import com.todomypet.petservice.dto.*;
import com.todomypet.petservice.exception.CustomException;
import com.todomypet.petservice.exception.ErrorCode;
import com.todomypet.petservice.repository.AdoptRepository;
import com.todomypet.petservice.repository.PetRepository;
import com.todomypet.petservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AdoptRepository adoptRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public String addPet(AddPetReqDTO addPetReqDTO) {
        Pet p = Pet.builder().id(addPetReqDTO.getId())
                    .petName(addPetReqDTO.getName())
                    .petMaxExperiencePoint(addPetReqDTO.getMaxExperience())
                    .petPortraitUrl(addPetReqDTO.getPortraitUrl())
                    .petDescribe(addPetReqDTO.getDescribe())
                    .petPersonality(PetPersonalityType.valueOf(addPetReqDTO.getPersonality()))
                    .petCondition(addPetReqDTO.getPetCondition())
                    .petType(addPetReqDTO.getType())
                    .petGrade(addPetReqDTO.getGrade())
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
                    .experiencePoint(adopt.getExperiencePoint())
                    .portraitUrl(pet.getPetPortraitUrl())
                    .grade(pet.getPetGrade())
                    .maxExperiencePoint(pet.getPetMaxExperiencePoint())
                    .signatureCode(adopt.getSignatureCode())
                    .build();
            adoptedPetResDTOList.add(adoptedPetResDTO);
        }
        return AdoptedPetResListDTO.builder().petList(adoptedPetResDTOList).build();
    }

    @Override
    public GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode) {
        List<Adopt> petInfos = adoptRepository.getMyPetInfo(userId, signatureCode);
        List<GetMyPetInfoResDTO> getMyPetInfoResDTOList = new ArrayList<GetMyPetInfoResDTO>();
        for (Adopt adopt : petInfos) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());

            GetMyPetInfoResDTO getMyPetInfoResDTO = GetMyPetInfoResDTO.builder()
                    .portraitUrl(pet.getPetPortraitUrl())
                    .name(adopt.getName())
                    .maxExperience(pet.getPetMaxExperiencePoint())
                    .experience(adopt.getExperiencePoint())
                    .grade(pet.getPetGrade())
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
                .grade(pet.getPetGrade())
                .name(adopt.getName())
                .type(pet.getPetType())
                .personality(pet.getPetPersonality())
                .description(pet.getPetDescribe())
                .portraitUrl(pet.getPetPortraitUrl())
                .build();
    }

    @Override
    public void renamePet(String userId, RenamePetReqDTO renamePetReqDTO) {
        Adopt adopt = adoptRepository.getOneAdoptByUserIdAndSignatureCode(userId, renamePetReqDTO.getSignatureCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_COLLECT_USER_AND_SIGNATURE_CODE));
        adoptRepository.renamePet(userId, renamePetReqDTO.getSignatureCode(), renamePetReqDTO.getRename());
    }

    @Override
    public GetPetCollectionListResDTO getPetCollection(String userId) {
        PetType[] petTypeList = {PetType.BREAD, PetType.GHOST};
        GetPetCollectionListResDTO getPetCollectionListResDTO = new GetPetCollectionListResDTO();

        for (int i = 0; i < petTypeList.length; i++) {
            List<Pet> petList = petRepository.getPetList(petTypeList[i]);
            List<GetPetCollectionResDTO> getPetCollectionResList = new ArrayList<>();
            for (int j = 0; j < petList.size(); j++) {
                Pet pet = petList.get(j);
                GetPetCollectionResDTO getPetCollectionResDTO = GetPetCollectionResDTO.builder()
                        .id(pet.getId())
                        .petName(pet.getPetName())
                        .portraitUrl(pet.getPetPortraitUrl())
                        .collected(adoptRepository.existsAdoptByUserIdAndPetId(userId, pet.getId()))
                        .describe(pet.getPetDescribe())
                        .personality(pet.getPetPersonality())
                        .grade(pet.getPetGrade())
                        .build();
                getPetCollectionResList.add(getPetCollectionResDTO);
            }
            switch (i) {
                case 0 -> {
                    getPetCollectionListResDTO.setBread(getPetCollectionResList);
                }
                case 1 -> {
                    getPetCollectionListResDTO.setGhost(getPetCollectionResList);
                }
            }
        }
        return getPetCollectionListResDTO;
    }

    @Override
    @Transactional
    public List<CommunityPetListResDTO> getCommunityPetList(String userId) {
        List<Adopt> adoptList = adoptRepository.getCommunityPetList(userId);
        List<CommunityPetListResDTO> communityPetListResDTOList = new ArrayList<>();
        for (Adopt adopt : adoptList) {
            communityPetListResDTOList.add(CommunityPetListResDTO.builder()
                    .id(adopt.getSeq())
                    .petName(adopt.getName())
                    .petImageUrl(petRepository.getPetBySeqOfAdopt(adopt.getSeq()).getPetPortraitUrl()).build());

        }
        return communityPetListResDTOList;
    }

    @Override
    @Transactional
    public UpdateExperiencePointResDTO updateExperiencePoint(String userId,
                                                             UpdateExperiencePointReqDTO updateExperiencePointReqDTO) {
        log.info(">>> 경험치 획득 진입: (유저)" + userId + "/ (펫 seqId)" +
                updateExperiencePointReqDTO.getPetSeqId() + " (기존 경험치)" + adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId()));
        if (adoptRepository.getAdoptBySeq(userId, updateExperiencePointReqDTO.getPetSeqId()) == null) {
            throw new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP);
        };

        adoptRepository.updateExperiencePoint(userId, updateExperiencePointReqDTO.getPetSeqId(),
                updateExperiencePointReqDTO.getExperiencePoint());

        int updatedExp = adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId());

        log.info(">>> 경험치 획득 완료: (유저)" + userId + "/ (펫 seqId)" +
                updateExperiencePointReqDTO.getPetSeqId() + " (갱신 경험치)" + adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId()));

        return UpdateExperiencePointResDTO.builder().updatedExperiencePoint(updatedExp).build();
    }

    @Override
    public String getMainPetByUserId(String userId) {
        log.info(">>> 서버간 통신 수신: 메인 펫 조회: " + userId);
        Adopt adopt = adoptRepository.getMainPetByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_MAIN_PET));
        return adopt.getSeq();
    }
}
