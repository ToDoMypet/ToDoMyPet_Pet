package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AdoptedPetResListDTO {
    List<AdoptedPetResDTO> petList;
}
