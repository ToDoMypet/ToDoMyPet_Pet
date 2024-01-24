package com.todomypet.petservice.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U001", "파일 업로드에 실패했습니다."),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U002", "존재하지 않는 유저입니다."),
    MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U003", "메일 전송에 실패했습니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "U004", "탈퇴한 회원입니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "U005", "이미 존재하는 이메일입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "U006", "토큰이 만료되었습니다."),
    DID_NOT_ACHIEVED_ACHIEVEMENT(HttpStatus.BAD_REQUEST, "U007", "완료하지 못한 업적은 조회할 수 없습니다."),
    ALREADY_ACHIEVE(HttpStatus.BAD_REQUEST, "U008", "이미 달성한 업적입니다."),
    ACHIEVEMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U009", "업적 id가 유효하지 않습니다."),
    NICKNAME_IS_NULL(HttpStatus.BAD_REQUEST, "U010", "닉네임 값이 null입니다."),
    NOT_COLLECT_USER_AND_SIGNATURE_CODE(HttpStatus.BAD_REQUEST, "U011",
            "유저 아이디와 펫 시그니처 코드가 일치하지 않습니다."),
    NOT_EXISTS_BACKGROUND(HttpStatus.BAD_REQUEST, "U012", "잘못된 펫룸 id입니다."),
    NOT_EXISTS_ADOPT_RELATIONSHIP(HttpStatus.BAD_REQUEST, "U013",
            "유저와 펫 사이에 입양 관계가 존재하지 않습니다."),
    NOT_EXISTS_MAIN_PET(HttpStatus.INTERNAL_SERVER_ERROR, "U014", "유저의 메인 펫이 존재하지 않습니다."),
    NOT_EXISTS_PET(HttpStatus.BAD_REQUEST, "U015", "잘못된 펫 아이디입니다."),
    EXPERIENCE_POINT_NOT_SATISFIED(HttpStatus.BAD_REQUEST, "U016", "경험치가 만족되지 않습니다."),
    FEIGN_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "U017", "서버간 통신 중 장애가 발생했습니다."),
    GRADUATION_MUST_BE_ADULT_GRADE(HttpStatus.BAD_REQUEST, "U018", "ADULT GRADE가 아닌 펫은 졸업할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
