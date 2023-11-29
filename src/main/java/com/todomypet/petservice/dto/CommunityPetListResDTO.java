package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommunityPetListResDTO {
    private String id;
    private String petImageUrl;
    private String petName;
}
