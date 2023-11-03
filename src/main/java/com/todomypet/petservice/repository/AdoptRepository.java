package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.relationship.Adopt;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@EnableNeo4jRepositories
@Repository
public interface AdoptRepository extends Neo4jRepository<Adopt, Long> {

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (p:Pet{id:$petId}) " +
            "CREATE (u)-[:ADOPT{rename:$rename, adoptAt:$adoptAt, seq: $seq}]->(p)")
    void createAdoptBetweenAdoptAndUser(String userId, String petId, String rename, LocalDateTime adoptAt, String seq);
}
