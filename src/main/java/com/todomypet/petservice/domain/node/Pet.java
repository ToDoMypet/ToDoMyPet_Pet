package com.todomypet.petservice.domain.node;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Pet")
@Getter
@Builder
public class Pet {
    @Id
    private String id;

    @Property("petName")
    private String petName;

    @Property("petType")
    private PetType petType;

    @Property("petMaxExperiencePoint")
    private int petMaxExperiencePoint;

    @Property("petGrade")
    private PetGradeType petGrade;

    @Property("petImageUrl")
    private String petImageUrl;

    @Property("petPortraitUrl")
    private String petPortraitUrl;

    @Property("petDescribe")
    private String petDescribe;

    @Property("petGif")
    private String petGif;

    @Property("petPersonality")
    private PetPersonalityType petPersonality;

    @Property("petCondition")
    private int petCondition;
}
