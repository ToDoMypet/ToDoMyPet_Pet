package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.openFeign.AchieveReqDTO;
import com.todomypet.petservice.dto.openFeign.CheckAchieveOrNotReqDTO;
import com.todomypet.petservice.dto.openFeign.CheckAchieveOrNotResDTO;
import com.todomypet.petservice.dto.openFeign.FeignClientResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.awt.datatransfer.Clipboard;

@FeignClient(name = "user-service", url="${feign.user.url}")
public interface UserServiceClient {
    @PutMapping(value = "/increase-collection-count", consumes = "application/json")
    FeignClientResDTO<Void> increaseCollectionCountByUserId(@RequestHeader String userId);

    @PutMapping(value = "/increase-and-get-pet-evolve-count", consumes = "application/json")
    FeignClientResDTO<Integer> increasePetEvolveCountByUserId(@RequestHeader String userId);

    @PutMapping(value = "/increase-and-get-pet-complete-count", consumes = "application/json")
    FeignClientResDTO<Integer> increaseAndGetPetCompleteCountByUserId(@RequestHeader String userId);

    @PostMapping(value = "/achievement/achieve-or-not", consumes = "application/json")
    FeignClientResDTO<CheckAchieveOrNotResDTO> checkAchieveOrNot(@RequestHeader String userId,
                                                                 @RequestBody CheckAchieveOrNotReqDTO req);

    @PostMapping(value = "/achievement", consumes = "application/json")
    FeignClientResDTO<Void> achieve(@RequestHeader String userId,
                                    @RequestBody AchieveReqDTO achieveReqDTO);

    @PutMapping(value = "/increase-and-get-pet-evolve-count", consumes = "application/json")
    FeignClientResDTO<Integer> increaseAndGetPetEvolveCountByUserId(String userId);
}
