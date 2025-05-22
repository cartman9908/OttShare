package seohan.ottshare.exception;

public class NotFoundSharingUserForRoom extends RuntimeException {
    public NotFoundSharingUserForRoom(Long roomId, Long userId) {
        super("해당 유저는 방 ID " + roomId + "에 존재하지 않습니다. (userId: " + userId + ")");
    }
}
