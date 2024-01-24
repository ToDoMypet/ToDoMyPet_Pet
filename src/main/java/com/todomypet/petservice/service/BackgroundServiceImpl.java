package com.todomypet.petservice.service;

import com.todomypet.petservice.domain.node.Background;
import com.todomypet.petservice.dto.background.AddBackgroundReqDTO;
import com.todomypet.petservice.dto.BackgroundResDTO;
import com.todomypet.petservice.dto.background.AddBackgroundReqDTOList;
import com.todomypet.petservice.exception.CustomException;
import com.todomypet.petservice.exception.ErrorCode;
import com.todomypet.petservice.mapper.BackgroundMapper;
import com.todomypet.petservice.repository.BackgroundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BackgroundServiceImpl implements BackgroundService {

    private final BackgroundRepository backgroundRepository;
    private final BackgroundMapper backgroundMapper;

    @Override
    public void addBackground(AddBackgroundReqDTOList req) {
        for (AddBackgroundReqDTO addBackgroundReqDTO : req.getBackgroundList()) {
            Background background = Background.builder().id(addBackgroundReqDTO.getId())
                    .backgroundImageUrl(addBackgroundReqDTO.getBackgroundImageUrl())
                    .build();
            backgroundRepository.save(background);
        }
    }

    @Override
    public String getBackgroundUrlById(String backgroundId) {
        return backgroundRepository.getBackgroundUrlById(backgroundId);
    }

    @Override
    public List<BackgroundResDTO> getBackgroundList() {
        List<Background> backgroundList = backgroundRepository.getBackgroundList();
        List<BackgroundResDTO> backgroundResDTOList = new ArrayList<>();
        for (Background background : backgroundList) {
            backgroundResDTOList.add(backgroundMapper.backgroundToBackgroundResDTO(background));
        }
        return backgroundResDTOList;
    }

    @Override
    @Transactional
    public void changeBackground(String userId, String backgroundId) {
        Background b = backgroundRepository.getBackgroundById(backgroundId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_BACKGROUND));
        backgroundRepository.deleteBackground(userId);
        backgroundRepository.changeBackground(userId, backgroundId);
    }
}
