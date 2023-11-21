package com.todomypet.petservice.dto;

import com.todomypet.petservice.domain.node.PetGradeType;
import com.todomypet.petservice.domain.node.PetPersonalityType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPetCollectionResDTO {
    private String id;
    private String petName;
    private String portraitUrl;
    private boolean collected;
    private PetPersonalityType personality;
    private PetGradeType grade;
    private String describe;
}
