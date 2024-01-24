package com.todomypet.petservice.dto.pet;

import com.todomypet.petservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAvailableStartingPetDTO {
    private String id;
    private PetGradeType petGrade;
    private String petName;
    private String petImageUrl;
}
