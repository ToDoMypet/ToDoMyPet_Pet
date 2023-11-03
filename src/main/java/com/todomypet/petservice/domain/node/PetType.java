package com.todomypet.petservice.domain.node;

import lombok.Getter;

@Getter
public enum PetType {
    BREAD("빵");
    ;

    private final String petType;

    PetType(String petType) {
        this.petType = petType;
    }
}
