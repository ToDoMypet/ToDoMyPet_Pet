package com.todomypet.petservice.domain.node;

import lombok.Getter;

@Getter
public enum PetPersonalityType {
    CALM("덤덤"),
    CHEERFUL("명랑"),
    GLUTTON("먹보"),
    PROTEIN("프로틴"),
    PURE_EVIL("순수악")
    ;

    private final String PetPersonalityType;

    PetPersonalityType(String petPersonalityType) {
        this.PetPersonalityType = petPersonalityType;
    }
}
