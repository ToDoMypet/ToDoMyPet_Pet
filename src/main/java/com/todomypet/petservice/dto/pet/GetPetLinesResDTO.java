package com.todomypet.petservice.dto.pet;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class GetPetLinesResDTO {
    private Map<String, String> resign;
    private Map<String, String> firstMeet;
    private Map<String, String> fullGauge;
    private Map<String, String> upgrade;
    private Map<String, List<String>> mainPageRandomLine;
}
