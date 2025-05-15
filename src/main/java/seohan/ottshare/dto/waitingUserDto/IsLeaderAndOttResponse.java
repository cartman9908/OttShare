package seohan.ottshare.dto.waitingUserDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seohan.ottshare.enums.OttType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IsLeaderAndOttResponse {

    @JsonProperty("isLeader")
    private boolean isLeader;

    private OttType ottType;
}
