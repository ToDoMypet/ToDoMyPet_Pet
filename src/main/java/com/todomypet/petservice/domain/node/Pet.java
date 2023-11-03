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

    @Property("name")
    private String name;

    @Property("type")
    private PetType type;

    @Property("maxExperience")
    private int maxExperience;

    @Property("grade")
    private PetGradeType grade;

    @Property("portraitUrl")
    private String portraitUrl;

    @Property("describe")
    private String describe;

    @Property("personality")
    private String personality;

    @Property("petCondition")
    private int petCondition;
}
