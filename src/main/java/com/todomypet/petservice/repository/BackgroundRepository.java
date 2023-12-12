package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.node.Background;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableNeo4jRepositories
@Repository
public interface BackgroundRepository extends Neo4jRepository<Background, String> {

    Optional<Background> getBackgroundById(String backgroundId);

    @Query("MATCH (b:Background{id:$backgroundId}) RETURN b.backgroundImageUrl")
    String getBackgroundUrlById(String backgroundId);

    @Query("MATCH (b:Background) RETURN b ORDER BY b.id")
    List<Background> getBackgroundList();

    @Query("MATCH (u:User{id:$userId}) WITH u MATCH (u)-[s:SELECT]->(b:Background) DELETE s")
    void deleteBackground(String userId);

    @Query("MATCH (u:User{id:$userId}) WITH u MATCH (b:Background{id:$backgroundId}) CREATE (u)-[:SELECT]->(b)")
    void changeBackground(String userId, String backgroundId);
}
