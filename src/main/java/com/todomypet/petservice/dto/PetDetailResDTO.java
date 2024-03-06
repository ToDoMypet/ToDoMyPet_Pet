package com.todomypet.petservice.dto;

import com.todomypet.petservice.domain.node.PetGradeType;
import com.todomypet.petservice.domain.node.PetPersonalityType;
import com.todomypet.petservice.domain.node.PetType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PetDetailResDTO {
    private PetGradeType grade;
    private String petOriginName;
    private String name;
    private PetType type;
    private PetPersonalityType personality;
    private String description;
    private String imageUrl;
}
