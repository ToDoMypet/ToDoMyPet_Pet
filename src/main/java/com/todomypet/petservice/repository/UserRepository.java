package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (u:User{id:$userId}) SET u.petEvolveCount = u.petEvolveCount + 1")
    void increasePetEvolveCountByUserId(String userId);
}
