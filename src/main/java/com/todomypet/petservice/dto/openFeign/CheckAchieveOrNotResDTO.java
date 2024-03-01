package com.todomypet.petservice.dto.openFeign;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckAchieveOrNotResDTO {
    private boolean achieveOrNot;
    private String achievementId;
}
