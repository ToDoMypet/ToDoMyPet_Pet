package com.todomypet.petservice.dto.pet;

import com.todomypet.petservice.domain.node.PetGradeType;
import com.todomypet.petservice.domain.node.PetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPetReqDTO {
    private String id;
    private String name;
    private PetType type;
    private int maxExperience;
    private PetGradeType grade;
    private String imageUrl;
    private String portraitUrl;
    private String gif;
    private String describe;
    private String personality;
    private int petCondition;
}
