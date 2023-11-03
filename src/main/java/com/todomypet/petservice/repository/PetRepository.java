package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.Pet;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Repository;

@EnableNeo4jRepositories
@Repository
public interface PetRepository extends Neo4jRepository<Pet, String> {
}
