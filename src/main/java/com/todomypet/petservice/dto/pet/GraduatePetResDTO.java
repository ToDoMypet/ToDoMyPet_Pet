package com.todomypet.petservice.dto.pet;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraduatePetResDTO {
    private String petImageUrl;
    private String petName;
    private boolean renameOrNot;
    private String originName;
    private String currentName;
}
