package com.example.howtoday.Calendar;

import android.graphics.drawable.Drawable;

public class ScheduleItem {
    private Drawable blackCircle;
    private String schedule;

    public Drawable getBlackCircle() {
        return blackCircle;
    }

    public void setBlackCircle(Drawable blackCircle) {
        this.blackCircle = blackCircle;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
