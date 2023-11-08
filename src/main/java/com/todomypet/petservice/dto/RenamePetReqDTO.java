package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RenamePetReqDTO {
    private String signatureCode;
    private String rename;
}
