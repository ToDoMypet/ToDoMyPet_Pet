package com.todomypet.petservice.dto;

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
}
