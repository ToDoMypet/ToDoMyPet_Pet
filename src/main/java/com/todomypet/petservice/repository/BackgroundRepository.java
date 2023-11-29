package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.node.Background;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@EnableNeo4jRepositories
@Repository
public interface BackgroundRepository extends Neo4jRepository<Background, String> {
    @Query("MATCH (b:Background{id:$backgroundId}) RETURN b.backgroundImageUrl")
    String getBackgroundUrlById(String backgroundId);
}
