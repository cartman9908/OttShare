package seohan.ottshare.exception;

public class SharingUserNotFoundException extends RuntimeException {
    public SharingUserNotFoundException(Long userId) {
        super("공유방에 해당하는 유저가 없습니다 id: " + userId);
    }
}
