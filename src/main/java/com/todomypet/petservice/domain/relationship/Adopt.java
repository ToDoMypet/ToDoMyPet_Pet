package com.todomypet.petservice.domain.relationship;

import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class Adopt {
    @RelationshipId
    private Long id;

    @Property
    private String seq;
}
