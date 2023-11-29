package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.AddBackgroundReqDTO;
import com.todomypet.petservice.dto.BackgroundResDTO;

import java.util.List;

public interface BackgroundService {
    void addBackground(AddBackgroundReqDTO addBackgroundReqDTO);
    String getBackgroundUrlById(String backgroundId);

    List<BackgroundResDTO> getBackgroundList();
}
