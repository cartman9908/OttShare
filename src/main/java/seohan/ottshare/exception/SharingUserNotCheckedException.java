package seohan.ottshare.exception;

public class SharingUserNotCheckedException extends RuntimeException {
    public SharingUserNotCheckedException(Long userId) {
        super(userId + "해당 유저는 체크하지 않았습니다.");
    }
}
