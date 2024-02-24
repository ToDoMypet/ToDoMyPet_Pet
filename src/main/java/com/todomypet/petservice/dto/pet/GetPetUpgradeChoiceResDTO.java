package com.todomypet.petservice.dto.pet;

import com.todomypet.petservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPetUpgradeChoiceResDTO {
    private String id;
    private String petName;
    private PetGradeType petGrade;
    private String petImageUrl;
    private boolean getOrNot;
}
