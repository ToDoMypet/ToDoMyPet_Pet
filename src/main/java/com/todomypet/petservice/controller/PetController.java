package com.todomypet.petservice.controller;

import com.todomypet.petservice.dto.AddPetReqDTO;
import com.todomypet.petservice.dto.AddPetResDTO;
import com.todomypet.petservice.dto.SuccessResDTO;
import com.todomypet.petservice.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        return new SuccessResDTO<>(response);
    }
}
