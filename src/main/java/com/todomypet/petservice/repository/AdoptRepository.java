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

    @Query("MATCH (u:User{id:$userId}) WITH u MATCH (p:Pet{id:$petId}) " +
            "CREATE (u)-[:ADOPT{name:$rename, seq: $seq, graduated: false, experiencePoint: 0, " +
            "signatureCode: $signatureCode, renameOrNot: $renameOrNot}]->(p)")
    void createAdoptBetweenAdoptAndUser(String userId, String petId,
                                        String rename, String seq, String signatureCode, boolean renameOrNot);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE (p.petGrade = 'ADULT' AND a.graduated = true) OR (a.graduated = false) " +
            "RETURN a{.seq, .name, .graduated, .experiencePoint, .signatureCode, .renameOrNot} ORDER BY a.seq DESC")
    List<Adopt> getAdoptList(String userId);

    @Query("MATCH (User)-[a:ADOPT]->(Pet) " +
            "WHERE a.signatureCode = $signatureCode RETURN a")
    Optional<Adopt> getOneAdoptBySignatureCode(String signatureCode);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(Pet) WHERE a.signatureCode = $signatureCode " +
            "RETURN a{.experiencePoint, .name, .seq, .graduated, .renameOrNot, .signatureCode} ORDER BY a.seq")
    List<Adopt> getMyPetInfo(String userId, String signatureCode);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.seq = $seq RETURN a{.seq, .name, .graduated, .experiencePoint, .signatureCode, .renameOrNot}")
    Optional<Adopt> getAdoptBySeq(String userId, String seq);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT{signatureCode:$signatureCode}]->(p:Pet) SET a.name = $rename, a.renameOrNot = true")
    void renamePet(String userId, String signatureCode, String rename);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.signatureCode = $signatureCode " +
            "RETURN a{.seq, .name, .graduated, .experiencePoint, .signatureCode, .renameOrNot}")
    List<Adopt> getAdoptByUserIdAndSignatureCode(String userId, String signatureCode);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (p:Pet{id:$petId}) RETURN EXISTS((u)-[:ADOPT]->(p))")
    boolean existsAdoptByUserIdAndPetId(String userId, String petId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE (p.petGrade = 'ADULT' AND a.graduated = true) OR (a.graduated = false) " +
            "RETURN a{.seq, .name, .graduated, .experiencePoint, .signatureCode, .renameOrNot} ORDER BY a.seq DESC")
    List<Adopt> getCommunityPetList(String userId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.seq = $petSeqId " +
            "SET a.experiencePoint = a.experiencePoint + $experiencePoint")
    void updateExperiencePoint(String userId, String petSeqId, int experiencePoint);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.seq = $petSeqId " +
            "RETURN a.experiencePoint")
    int getExperiencePointBySeqId(String userId, String petSeqId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.graduated = false " +
            "RETURN a{.seq, .name, .graduated, .experiencePoint, .signatureCode, .renameOrNot}")
    Optional<Adopt> getMainPetByUserId(String userId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[a:ADOPT]->(p:Pet) WHERE a.seq = $petSeq " +
            "SET a.graduated = true")
    void graduatePetBySeq (String userId, String petSeq);
}
