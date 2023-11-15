package com.todomypet.petservice.repository;

import com.todomypet.petservice.domain.relationship.Adopt;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableNeo4jRepositories
@Repository
public interface AdoptRepository extends Neo4jRepository<Adopt, Long> {

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (p:Pet{id:$petId}) " +
            "CREATE (u)-[:ADOPT{name:$rename, startedAt:$adoptAt, " +
            "seq: $seq, graduated: false, experience: 0, signatureCode: $signatureCode}]->(p)")
    void createAdoptBetweenAdoptAndUser(String userId, String petId, String rename, LocalDateTime adoptAt,
                                        String seq, String signatureCode);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE (p.grade = 'ADULT' AND a.graduated = true) OR (a.graduated = false) " +
            "RETURN a{.seq, .name, .graduated, .experience} ORDER BY a.seq DESC")
    List<Adopt> getAdoptList(String userId);

    @Query("MATCH (User)-[a:ADOPT]->(Pet) " +
            "WHERE a.signatureCode = $signatureCode RETURN a")
    Optional<Adopt> getOneAdoptBySignatureCode(String signatureCode);

    @Query("MATCH (u:User:{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(Pet) WHERE a.signatureCode = $signatureCode " +
            "RETURN a{.experience, .name, .seq, .graduated} ORDER BY a.seq")
    List<Adopt> getMyPetInfo(String userId, String signatureCode);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.seq = $seq")
    Adopt getAdoptBySeq(String userId, String seq);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) SET a.name = $rename")
    void renamePet(String userId, String signatureCode, String rename);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.signatureCode = $signatureCode")
    Optional<Adopt> getOneAdoptByUserIdAndSignatureCode(String userId, String signatureCode);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (p:Pet{id:$petId}) RETURN EXISTS((u)-[:ADOPT]->(p))")
    boolean existsAdoptByUserIdAndPetId(String userId, String petId);
}
