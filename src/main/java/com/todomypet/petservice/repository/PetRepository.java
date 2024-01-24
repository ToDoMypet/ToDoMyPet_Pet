package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.node.Pet;
import com.todomypet.petservice.domain.node.PetGradeType;
import com.todomypet.petservice.domain.node.PetType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableNeo4jRepositories
@Repository
public interface PetRepository extends Neo4jRepository<Pet, String> {
    @Query("MATCH (u:User)-[a:ADOPT]->(p:Pet) " +
            "WHERE a.seq = $seq RETURN p")
    Pet getPetBySeqOfAdopt(String seq);

    @Query("MATCH (p:Pet{petType:$petType}) RETURN p ORDER BY p.id ASC")
    List<Pet> getPetList(PetType petType);

    @Query("MATCH (p:Pet{id:$petId}) RETURN p")
    Optional<Pet> getPetByPetId(String petId);

    @Query("MATCH (p:Pet) WHERE p.petGrade = $newGrade AND p.petType = $petType RETURN p")
    List<Pet> getPetByGradeAndType(PetGradeType newGrade, PetType petType);

    @Query("MATCH (u:User{id:$userId}) WITH u MATCH (u)-[:AVAILABLE]->(p:Pet) RETURN p ORDER BY p.id ASC")
    List<Pet> getAvailablePet(String userId);
}
