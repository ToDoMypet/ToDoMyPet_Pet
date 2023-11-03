package com.todomypet.petservice.domain;

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
