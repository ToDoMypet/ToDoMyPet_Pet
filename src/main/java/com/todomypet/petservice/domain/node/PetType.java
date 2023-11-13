package com.todomypet.petservice.domain.node;

import lombok.Getter;

@Getter
public enum PetType {
    BREAD("빵"),
    GHOST("유령");
    ;

    private final String petType;

    PetType(String petType) {
        this.petType = petType;
    }
}
