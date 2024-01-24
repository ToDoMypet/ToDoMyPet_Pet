package com.todomypet.petservice.dto;

import lombok.Builder;

@Builder
public class UpgradePetResDTO {
    private boolean renameOrNot;
    private String originName;
    private String currentName;
    private String petImageUrl;
}
