package com.todomypet.petservice.controller;

import com.todomypet.petservice.dto.*;
import com.todomypet.petservice.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    // todo: 권한 설정 필요
    @Operation(summary = "펫 추가", description = "펫을 추가합니다. admin 전용 API입니다.")
    @PostMapping("/add")
    public SuccessResDTO<AddPetResDTO> addPet(@RequestBody AddPetReqDTO addPetReqDTO) {
        AddPetResDTO response = AddPetResDTO.builder().id(petService.addPet(addPetReqDTO)).build();
        return new SuccessResDTO<AddPetResDTO>(response);
    }

    @Operation(summary = "펫 입양", description = "키우는 펫을 추가합니다.")
    @PostMapping("/adopt")
    public SuccessResDTO<Void> adoptPet(@RequestHeader String userId,
                                        @RequestBody AdoptPetReqDTO adoptPetReqDTO) {
        petService.adoptPet(userId, adoptPetReqDTO);
        return new SuccessResDTO<>(null);
    }

    @Operation(summary = "일지", description = "일지 데이터를 불러옵니다.")
    @GetMapping("/adopted-pet-list")
    public SuccessResDTO<AdoptedPetResListDTO> getAdoptedPetList(@RequestHeader String userId) {
        AdoptedPetResListDTO response = petService.getAdoptedPetList(userId);
        return new SuccessResDTO<AdoptedPetResListDTO>(response);
    }

    @Operation(summary = "내 펫 정보", description = "일지에서 펫을 선택했을 때 불러오는 데이터입니다.")
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

    @GetMapping("/main-pet")
    public SuccessResDTO<String> getMainPet(@RequestHeader String userId) {
        String response = petService.getMainPetByUserId(userId);
        return new SuccessResDTO<>(response);
    }
}
