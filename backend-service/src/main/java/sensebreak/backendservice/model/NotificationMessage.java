package sensebreak.backendservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationMessage {
    private String userId;
    private String type;
    private String content;

    public NotificationMessage(String userId, String type, String content) {
        this.userId = userId;
        this.type = type;
        this.content = content;
    }

}
