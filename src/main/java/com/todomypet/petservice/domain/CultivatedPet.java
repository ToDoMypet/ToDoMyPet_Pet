package com.todomypet.petservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CultivatedPet {
    @Id
    private String id;
}
