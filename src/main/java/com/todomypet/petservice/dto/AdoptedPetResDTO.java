package com.todomypet.petservice.dto;

import com.todomypet.petservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdoptedPetResDTO {
    private String name;
    private String imageUrl;
    private PetGradeType grade;
    private int experiencePoint;
    private int maxExperiencePoint;
    private String signatureCode;
    private boolean graduated;
}
