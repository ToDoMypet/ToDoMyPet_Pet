package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMyPetInfoReqDTO {
    private String signatureCode;
}
