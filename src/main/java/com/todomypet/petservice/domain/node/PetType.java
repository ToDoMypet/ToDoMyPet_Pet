package com.todomypet.petservice.domain.node;

import lombok.Getter;

@Getter
public enum PetType {
    BREAD("빵"),
    CREAM("크림"),
    OVERCOOKED_BREAD("과발효된 빵"),
    COOKIE("쿠키"),
    GHOST("유령"),
    ZOMBIE("좀비"),
    CURSE("저주"),
    SLASHER("슬래셔"),
    GANG("갱단"),
    CUP("컵"),
    DUST("먼지"),
    MELON("멜론"),
    PUDDING("푸딩"),
    HOT_CAKE("핫케이크"),
    CHRISTMAS("크리스마스"),
    FIRE("불꽃"),
    ICE("얼음"),
    RAINBOW("무지개"),
    MILLIONAIRE("백만장자"),
    GOLD("황금"),
    SLEEPY("슬리피"),
    DEVIL("악마"),
    ANGEL("천사"),
    FAIRY("요정"),
    MAGICAL_GIRL("마법소녀"),
    CLOVER("클로버"),
    CHERRY_BLOSSOM("벚꽃")
    ;

    private final String petType;

    PetType(String petType) {
        this.petType = petType;
    }
}
