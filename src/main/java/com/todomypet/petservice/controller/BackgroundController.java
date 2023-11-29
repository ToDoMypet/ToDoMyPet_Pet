package com.todomypet.petservice.controller;

import com.todomypet.petservice.dto.AddBackgroundReqDTO;
import com.todomypet.petservice.dto.BackgroundResDTO;
import com.todomypet.petservice.dto.SuccessResDTO;
import com.todomypet.petservice.service.BackgroundService;
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
@Tag(name = "Background Controller")
public class BackgroundController {

    private final BackgroundService backgroundService;

    // todo: 권한 설정 필요
    @Operation(summary = "배경 추가", description = "어드민 전용 API입니다.")
    @PostMapping("/background/add")
    public SuccessResDTO<Void> addBackground(@RequestBody AddBackgroundReqDTO addBackgroundReqDTO) {
        backgroundService.addBackground(addBackgroundReqDTO);
        return new SuccessResDTO<>(null);
    }

    @Operation(hidden = true)
    @GetMapping("/background/{backgroundId}")
    public SuccessResDTO<String> getBackgroundUrl(@PathVariable String backgroundId) {
        String response = backgroundService.getBackgroundUrlById(backgroundId);
        return new SuccessResDTO<>(response);
    }

    @GetMapping("/background")
    public SuccessResDTO<List<BackgroundResDTO>> getBackgroundList(@RequestHeader String userId) {
        List<BackgroundResDTO> response = backgroundService.getBackgroundList();
        return new SuccessResDTO<List<BackgroundResDTO>>(response);
    }

}
