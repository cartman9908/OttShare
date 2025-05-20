package seohan.ottshare.exception;

import seohan.ottshare.enums.OttType;

public class NotOttLeaderException extends RuntimeException {
    public NotOttLeaderException(OttType ottType) {
        super(ottType + " 대한 리더가 아닌 사용자를 찾을 ");
    }
}
