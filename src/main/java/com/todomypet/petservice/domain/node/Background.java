package com.todomypet.petservice.domain.node;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Background")
@Builder
public class Background {
    @Id
    private String id;
    @Property("backgroundImageUrl")
    private String backgroundImageUrl;
}
