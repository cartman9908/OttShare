package seohan.ottshare.exception;

public class OttShareRoomNotFoundException extends RuntimeException {
    public OttShareRoomNotFoundException(Long roomId) {
        super("해당 방은 존재 하지 않습니다 id" + roomId);
    }
}
