package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpgradePetReqDTO {
    private String seq;
    private String signatureCode;
    private String selectedPetId;
    private String petName;
}
