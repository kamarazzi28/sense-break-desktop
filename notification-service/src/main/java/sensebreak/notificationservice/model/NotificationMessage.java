package sensebreak.notificationservice.model;

public class NotificationMessage {
    private String userId;
    private String type;
    private String content;

    public NotificationMessage() {
    }

    public NotificationMessage(String userId, String type, String content) {
        this.userId = userId;
        this.type = type;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
