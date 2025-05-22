package seohan.ottshare.exception;

public class SharingUserNotCheckedException extends RuntimeException {
    public SharingUserNotCheckedException(Long userId) {
<<<<<<< HEAD
        super(userId + "해당 유저는 체크하지 않았습니다.");
=======
        super("해당 유저는 체크하지 않았습니다 id" + userId);
>>>>>>> a9e6092 (JPA 변경감지 지연 오류 해결)
    }
}
