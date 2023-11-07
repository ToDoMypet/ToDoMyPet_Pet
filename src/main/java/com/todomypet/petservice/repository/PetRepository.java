package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.dto.GetAdoptedPetDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableNeo4jRepositories
@Repository
public interface PetRepository extends Neo4jRepository<Pet, String> {

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE (p.grade = 'ADULT' AND a.graduated = true) OR (a.graduated = false) " +
            "RETURN p ORDER BY a.seq")
    List<Pet> getAdoptedPetList(String userId);

    @Query("MATCH (u:User)-[a:ADOPT]->(p:Pet) " +
            "WHERE a.seq = $seq RETURN p")
    Pet getPetBySeqOfAdopt(String seq);
}
