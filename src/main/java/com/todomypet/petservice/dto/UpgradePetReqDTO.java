package com.todomypet.petservice.dto;

import com.todomypet.petservice.domain.node.PetPersonalityType;
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
