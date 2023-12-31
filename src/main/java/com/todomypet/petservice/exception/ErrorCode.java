package com.todomypet.petservice.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_COLLECT_USER_AND_SIGNATURE_CODE(HttpStatus.BAD_REQUEST, "P001",
            "유저 아이디와 펫 시그니처 코드가 일치하지 않습니다."),
    NOT_EXISTS_BACKGROUND(HttpStatus.BAD_REQUEST, "P002", "잘못된 펫룸 id입니다."),
    NOT_EXISTS_ADOPT_RELATIONSHIP(HttpStatus.BAD_REQUEST, "P003",
            "유저와 펫 사이에 입양 관계가 존재하지 않습니다."),
    NOT_EXISTS_MAIN_PET(HttpStatus.INTERNAL_SERVER_ERROR, "P004", "유저의 메인 펫이 존재하지 않습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
