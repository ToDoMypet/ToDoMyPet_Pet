package com.todomypet.petservice.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddPetReqDTOList {
    List<AddPetReqDTO> petList;
}
