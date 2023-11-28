package com.todomypet.petservice.controller;

import com.todomypet.petservice.dto.AddBackgroundReqDTO;
import com.todomypet.petservice.dto.SuccessResDTO;
import com.todomypet.petservice.service.BackgroundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Background Controller")
public class BackgroundController {

    private final BackgroundService backgroundService;

    // todo: 권한 설정 필요
    @PostMapping("/background/add")
    @Operation(summary = "배경 추가", description = "어드민 전용 API입니다.")
    public SuccessResDTO<Void> addBackground(@RequestBody AddBackgroundReqDTO addBackgroundReqDTO) {
        backgroundService.addBackground(addBackgroundReqDTO);
        return new SuccessResDTO<>(null);
    }

}
