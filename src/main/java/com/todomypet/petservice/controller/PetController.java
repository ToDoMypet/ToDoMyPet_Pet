package com.todomypet.petservice.controller;

import com.todomypet.petservice.dto.*;
import com.todomypet.petservice.dto.pet.*;
import com.todomypet.petservice.service.PetService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pet Controller")
public class PetController {

    private final PetService petService;

    @Operation(summary = "펫 대사를 조회합니다.")
    @GetMapping("/get-pet-line")
    public SuccessResDTO<GetPetLinesResDTO> getPetLines() {
        GetPetLinesResDTO response = petService.getPetLines();
        return new SuccessResDTO<GetPetLinesResDTO>(response);
    }

    // todo: 권한 설정 필요
    @Operation(summary = "펫 추가", description = "펫을 추가합니다. admin 전용 API입니다.")
    @PostMapping("/add")
    public SuccessResDTO<Void> addPet(@RequestBody AddPetReqDTOList addPetReqDTO) {
        petService.addPet(addPetReqDTO);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "펫 입양", description = "키우는 펫을 추가합니다.")
    @PostMapping("/adopt")
    public SuccessResDTO<Void> adoptPet(@RequestHeader String userId,
                                        @RequestBody AdoptPetReqDTO adoptPetReqDTO) {
        petService.adoptPet(userId, adoptPetReqDTO);
        return new SuccessResDTO<>(null);
    }

    @Operation(summary = "입양 가능 스타팅 펫 조회")
    @GetMapping("/get-available-pet")
    public SuccessResDTO<List<GetAvailableStartingPetDTO>> getAvailableStartingPet(@RequestHeader String userId) {
        List<GetAvailableStartingPetDTO> response = petService.getAvailableStartingPet(userId);
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "일지", description = "일지 데이터를 불러옵니다.")
    @GetMapping("/adopted-pet-list")
    public SuccessResDTO<AdoptedPetResListDTO> getAdoptedPetList(@RequestHeader String userId) {
        AdoptedPetResListDTO response = petService.getAdoptedPetList(userId);
        return new SuccessResDTO<AdoptedPetResListDTO>(response);
    }

    @Operation(summary = "펫 성장 과정 조회", description = "일지에서 펫을 선택했을 때 불러오는 데이터입니다.")
    @GetMapping("/adopted-pet-list/my-pet-info/{signatureCode}")
    public SuccessResDTO<GetMyPetInfoResListDTO> getMyPetInfo(@RequestHeader String userId,
                                                              @PathVariable String signatureCode) {
        GetMyPetInfoResListDTO response = petService.getMyPetInfo(userId, signatureCode);
        return new SuccessResDTO<GetMyPetInfoResListDTO>(response);
    }

    @Operation(summary = "내 펫 정보 자세히 보기", description = "특정 펫의 데이터를 return합니다.")
    @GetMapping("/adopted-pet-list/my-pet-info/detail/{seq}")
    public SuccessResDTO<PetDetailResDTO> getMyPetDetail(@RequestHeader String userId,
                                                         @PathVariable String seq) {
        PetDetailResDTO response = petService.getPetDetail(userId, seq);
        return new SuccessResDTO<PetDetailResDTO>(response);
    }

    @Operation(summary = "펫 이름 새로 짓기", description = "펫 이름을 변경합니다.")
    @PutMapping("/rename")
    public SuccessResDTO<Void> renamePet(@RequestHeader String userId,
                                         @RequestBody RenamePetReqDTO renamePetReqDTO) {
        petService.renamePet(userId, renamePetReqDTO);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "도감 조회", description = "펫 도감을 조회합니다.")
    @GetMapping("/collection")
    public SuccessResDTO<GetPetCollectionListResDTO> getPetCollection(@RequestHeader String userId) {
        GetPetCollectionListResDTO response = petService.getPetCollection(userId);
        return new SuccessResDTO<GetPetCollectionListResDTO>(response);
    }

    @Operation(summary = "게시글 작성 시 펫 리스트 조회", description = "게시글 작성 시 펫을 선택하는 리스트를 조회합니다.")
    @GetMapping("/community-pet-list")
    public SuccessResDTO<List<CommunityPetListResDTO>> getCommunityPetList(@RequestHeader String userId) {
        List<CommunityPetListResDTO> response = petService.getCommunityPetList(userId);
        return new SuccessResDTO<List<CommunityPetListResDTO>>(response);
    }

    @Operation(summary = "펫 경험치 획득", description = "현재 키우고 있는 펫의 경험치를 획득합니다.")
    @PutMapping("/update-exp")
    public SuccessResDTO<UpdateExperiencePointResDTO> updateExperiencePoint(@RequestHeader String userId,
                                                                            @RequestBody UpdateExperiencePointReqDTO req) {
        UpdateExperiencePointResDTO response = petService.updateExperiencePoint(userId, req);
        return new SuccessResDTO<UpdateExperiencePointResDTO>(response);
    }

    @Operation(summary = "진화 선택 리스트 조회", description = "펫 진화 모달 안에 표시될 펫들의 정보를 조회합니다.")
    @GetMapping("/upgrade-choice/{petId}")
    public SuccessResDTO<List<GetPetUpgradeChoiceResDTO>> getPetListInUpgradeChoiceModal(@RequestHeader String userId,
                                                                                         @PathVariable String petId) {
        List<GetPetUpgradeChoiceResDTO> response = petService.getPetUpgradeChoice(userId, petId);
        return new SuccessResDTO<List<GetPetUpgradeChoiceResDTO>>(response);
    }

    @Operation(summary = "펫 진화", description = "펫을 진화시킵니다.")
    @PostMapping("/evolve")
    public SuccessResDTO<UpgradePetResDTO> evolvePet(@RequestHeader String userId,
                                                     @RequestBody UpgradePetReqDTO req) {
        UpgradePetResDTO response = petService.evolvePet(userId, req);
        return new SuccessResDTO<UpgradePetResDTO>(response);
    }

    @Operation(summary = "펫 졸업", description = "펫을 졸업시킵니다.")
    @PostMapping("/graduate")
    public SuccessResDTO<GraduatePetResDTO> graduatePet(@RequestHeader String userId,
                                                        @RequestBody GraduatePetReqDTO req) {
        GraduatePetResDTO response = petService.graduatePet(userId, req);
        return new SuccessResDTO<GraduatePetResDTO>(response);
    }

    @Operation(summary = "메인 펫 조회", description = "user id를 통해 main pet의 seq 값을 조회합니다.")
    @GetMapping("/get-main-pet")
    public SuccessResDTO<String> getMainPetSeq(@RequestHeader String userId) {
        String response = petService.getMainPetSeqByUserId(userId);
        return new SuccessResDTO<String>(response);
    }

    @Hidden
    @GetMapping("/get-main-pet-infos")
    public SuccessResDTO<GetMainPetInfosResDTO> getMainPetInfos(@RequestHeader String userId) {
        GetMainPetInfosResDTO response = petService.getMainPetInfosByUserId(userId);
        return new SuccessResDTO<>(response);
    }
}
