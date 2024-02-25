package com.todomypet.petservice.controller;

import com.todomypet.petservice.dto.SuccessResDTO;
import com.todomypet.petservice.dto.pet.AddPetReqDTOList;
import com.todomypet.petservice.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/admin")
@Tag(name = "Admin Controller")
public class AdminController {

    private final PetService petService;

    @Operation(summary = "펫 추가", description = "펫을 추가합니다. admin 전용 API입니다.")
    @PostMapping("/add")
    public SuccessResDTO<Void> addPet(@RequestBody AddPetReqDTOList addPetReqDTO) {
        petService.addPet(addPetReqDTO);
        return new SuccessResDTO<Void>(null);
    }
}
