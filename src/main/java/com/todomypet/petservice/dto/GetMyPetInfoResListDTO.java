package com.todomypet.petservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class GetMyPetInfoResListDTO {
    private List<GetMyPetInfoResDTO> petInfoList;
}
