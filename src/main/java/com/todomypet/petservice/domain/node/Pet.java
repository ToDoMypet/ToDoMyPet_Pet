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

<<<<<<< HEAD
    @Property("maxExperiencePoint")
    private int maxExperiencePoint;
=======
    @Property("petMaxExperiencePoint")
    private int petMaxExperiencePoint;
>>>>>>> d6e51293052926cf486a4ee3539198148e5ecb09

    @Property("petGrade")
    private PetGradeType petGrade;

    @Property("petPortraitUrl")
    private String petPortraitUrl;

    @Property("petDescribe")
    private String petDescribe;

    @Property("petPersonality")
    private String petPersonality;

    @Property("petCondition")
    private int petCondition;
}
