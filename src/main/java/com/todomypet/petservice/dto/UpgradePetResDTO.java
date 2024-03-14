package com.todomypet.petservice.dto;

import com.todomypet.petservice.domain.node.PetPersonalityType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpgradePetResDTO {
    private boolean renameOrNot;
    private String originName;
    private String currentName;
    private String selectPetOriginName;
    private String petImageUrl;
    private PetPersonalityType petPersonalityType;
}
