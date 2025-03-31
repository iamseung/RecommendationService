package com.example.recommendationservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_CATEGORY("존재하지 않는 카테고리 입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_BRAND("존재하지 않는 브랜드 입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_BRAND_NAME("이미 존재하는 브랜드 이름입니다.", HttpStatus.CONFLICT),
    NOT_EXIST_PRODUCT("존재하지 않는 상풉입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_PRODUCT("해당 브랜드-카테고리 조합의 상품이 이미 존재합니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}
