package sensebreak.gui.dto;

public class ReminderSettings {
    private boolean remindersEnabled;
    private int reminderIntervalMinutes;

    public boolean isRemindersEnabled() {
        return remindersEnabled;
    }

    public void setRemindersEnabled(boolean remindersEnabled) {
        this.remindersEnabled = remindersEnabled;
    }

    public int getReminderIntervalMinutes() {
        return reminderIntervalMinutes;
    }

    public void setReminderIntervalMinutes(int reminderIntervalMinutes) {
        this.reminderIntervalMinutes = reminderIntervalMinutes;
    }
}
