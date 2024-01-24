package com.todomypet.petservice.service;

import com.todomypet.petservice.dto.openFeign.FeignClientResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url="${feign.user.url}")
public interface UserServiceClient {
    @PutMapping(value = "/increase-collection-count", consumes = "application/json")
    FeignClientResDTO<Void> increaseCollectionCountByUserId(@RequestHeader String userId);

    @PutMapping(value = "/increase-pet-acquire-count", consumes = "application/json")
    FeignClientResDTO<Void> increasePetAcquireCountByUserId(@RequestHeader String userId);

    @PutMapping(value = "/increase-pet-evolve-count", consumes = "application/json")
    FeignClientResDTO<Void> increasePetEvolveCountByUserId(@RequestHeader String userId);

    @PutMapping(value = "/increase-pet-complete-count", consumes = "application/json")
    FeignClientResDTO<Void> increasePetCompleteCountByUserId(@RequestHeader String userId);
}
