package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdoptPetReqDTO {
    private String petId;
    private String rename;
}
