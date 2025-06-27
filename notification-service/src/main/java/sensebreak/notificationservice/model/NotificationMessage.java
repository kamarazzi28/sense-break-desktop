package sensebreak.notificationservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationMessage {
    private String userId;
    private String type;
    private String content;
}
