package com.sh.membership.common.response

enum class ErrorCode(val errorMsg:String) {

    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    BARCODE_GENERATE_FAIL("바코드생성에 실패했습니다."),
    BARCODE_NOT_FOUND("등록되지 않은 바코드입니다."),

    STORE_NOT_FOUND("등록되지 않은 상점입니다."),

    POINT_NOT_ENOUGH("포인트 부족으로 사용할수 없습니다."),
}