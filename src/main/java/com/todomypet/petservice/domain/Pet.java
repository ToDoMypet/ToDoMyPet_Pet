package com.todomypet.petservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pet {
    @Id
    private String id;
    private String name;
    private int maxExperience;
    private int level;
    private String portraitUrl;
    private String describe;
}
