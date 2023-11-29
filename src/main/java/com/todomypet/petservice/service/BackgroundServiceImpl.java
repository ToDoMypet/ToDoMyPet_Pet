package com.todomypet.petservice.service;

import com.todomypet.petservice.domain.node.Background;
import com.todomypet.petservice.dto.AddBackgroundReqDTO;
import com.todomypet.petservice.repository.BackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BackgroundServiceImpl implements BackgroundService {

    private final BackgroundRepository backgroundRepository;

    @Override
    public void addBackground(AddBackgroundReqDTO addBackgroundReqDTO) {
        Background background = Background.builder().id(addBackgroundReqDTO.getId())
                .backgroundImageUrl(addBackgroundReqDTO.getBackgroundImageUrl())
                .build();
        backgroundRepository.save(background);
    }

    @Override
    public String getBackgroundUrlById(String backgroundId) {
        return backgroundRepository.getBackgroundUrlById(backgroundId);
    }
}
