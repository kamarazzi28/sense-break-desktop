package sensebreak.backendservice.user.dto;

import lombok.Data;

@Data
public class ReminderSettings {
    private boolean remindersEnabled;
    private int reminderIntervalMinutes;
}
