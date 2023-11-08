package com.todomypet.petservice.domain.relationship;

import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.domain.node.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
@Getter
@Builder
public class Adopt {
    @RelationshipId
    private Long id;

    @Property("name")
    private String name;

    @Property("seq")
    private String seq;

    @Property("startedAt")
    private LocalDateTime startedAt;

    @Property("endedAt")
    private LocalDateTime endedAt;

    @Property("graduated")
    private Boolean graduated;

    @Property("experience")
    private int experience;

    @Property("signatureCode")
    private String signatureCode;

    @TargetNode
    private Pet pet;
}
