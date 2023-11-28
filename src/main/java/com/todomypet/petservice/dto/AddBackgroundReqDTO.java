package com.todomypet.petservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBackgroundReqDTO {
    private String id;
    private String backgroundImageUrl;
}
