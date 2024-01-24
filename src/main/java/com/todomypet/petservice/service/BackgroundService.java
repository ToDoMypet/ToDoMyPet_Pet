package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.background.AddBackgroundReqDTO;
import com.todomypet.petservice.dto.BackgroundResDTO;
import com.todomypet.petservice.dto.background.AddBackgroundReqDTOList;

import java.util.List;

public interface BackgroundService {
    void addBackground(AddBackgroundReqDTOList addBackgroundreqDTOList);
    String getBackgroundUrlById(String backgroundId);

    List<BackgroundResDTO> getBackgroundList();

    void changeBackground(String userId, String backgroundId);
}
