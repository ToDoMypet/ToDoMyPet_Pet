package com.todomypet.petservice.dto;

import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.domain.node.User;
import com.todomypet.petservice.domain.relationship.Adopt;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAdoptedPetDTO {
    User user;
    Pet pet;
    Adopt adopt;
}
