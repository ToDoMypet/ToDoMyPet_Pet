package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPetCollectionResDTO {
    private String id;
    private String petName;
    private String portraitUrl;
    private boolean collected;
}
