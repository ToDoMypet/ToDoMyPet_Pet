package com.todomypet.petservice.dto.pet;

import com.todomypet.petservice.domain.node.PetPersonalityType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMainPetInfosResDTO {
    private String petGrade;
    private String petPortraitImageUrl;
    private String petGifUrl;
    private String petName;
    private int petExperiencePoint;
    private int petMaxExperiencePoint;
    private PetPersonalityType petPersonalityType;
    private String petSignatureCode;
    private String petSeq;
}
