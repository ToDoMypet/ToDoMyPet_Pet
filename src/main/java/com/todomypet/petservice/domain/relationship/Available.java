package com.todomypet.petservice.domain.relationship;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
@Getter
@Builder
public class Available {
    @RelationshipId
    private Long id;
}
