package com.todomypet.petservice.dto.pet;

import com.todomypet.petservice.dto.GetPetCollectionListResDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
@Builder
public class GetPetCollectionListMapResDTO {
    HashMap<String, GetPetCollectionListResDTO> collectionList;
}
