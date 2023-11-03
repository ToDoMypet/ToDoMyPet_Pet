package com.todomypet.petservice.domain.node;

import lombok.Getter;

@Getter
public enum PetGradeType {
    BABY("아기"),
    CHILDREN("어린이"),
    TEENAGER("청소년"),
    ADULT("성인")
    ;

    private final String petGradeType;

    PetGradeType(String petGradeType) {
        this.petGradeType = petGradeType;
    }
}
