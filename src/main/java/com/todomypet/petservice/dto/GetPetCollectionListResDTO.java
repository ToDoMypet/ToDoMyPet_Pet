package com.todomypet.petservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Builder
public class GetPetCollectionListResDTO {
    HashMap<String, List<GetPetCollectionResDTO>> collectionList;
}
