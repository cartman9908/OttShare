package seohan.ottshare.exception;

import lombok.Getter;
import seohan.ottshare.enums.ErrorCode;

@Getter
public class LockAcquisitionException extends RuntimeException {

    private final ErrorCode errorCode;

    public LockAcquisitionException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
