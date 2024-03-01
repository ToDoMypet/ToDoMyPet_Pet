package com.todomypet.petservice.dto.openFeign;

import com.todomypet.petservice.domain.node.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckAchieveOrNotReqDTO {
    private AchievementType type;
    private int condition;
}
