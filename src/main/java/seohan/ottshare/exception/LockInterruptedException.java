package seohan.ottshare.exception;

import lombok.Getter;
import seohan.ottshare.enums.ErrorCode;

@Getter
public class LockInterruptedException extends RuntimeException {

    private final ErrorCode errorCode;

    public LockInterruptedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
