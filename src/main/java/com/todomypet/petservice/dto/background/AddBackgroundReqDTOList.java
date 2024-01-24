package com.todomypet.petservice.dto.background;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBackgroundReqDTOList {
    List<AddBackgroundReqDTO> backgroundList;
}
