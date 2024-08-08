package com.danjam.exception;

// 예외 관련 클래스를 모아 두는 익셉션 패키지

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);

    }

}
