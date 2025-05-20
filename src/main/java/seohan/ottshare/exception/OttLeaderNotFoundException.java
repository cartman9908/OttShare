package seohan.ottshare.exception;

import seohan.ottshare.enums.OttType;

public class OttLeaderNotFoundException extends RuntimeException {
    public OttLeaderNotFoundException(OttType ottType) {
        super(ottType+ "에 대한 리더를 찾을 수 없습니다.");
    }
}
