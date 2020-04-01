package com.ibm.sba.model;

import java.util.Date;

public class MentorFilter {
    private Integer skillId;
    private String startTimeSlot;
    private String endTimeSlot;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getStartTimeSlot() {
        return startTimeSlot;
    }

    public void setStartTimeSlot(String startTimeSlot) {
        this.startTimeSlot = startTimeSlot;
    }

    public String getEndTimeSlot() {
        return endTimeSlot;
    }

    public void setEndTimeSlot(String endTimeSlot) {
        this.endTimeSlot = endTimeSlot;
    }

    @Override
    public String toString() {
        return "MentorFilter{" +
                "skillId=" + skillId +
                ", startTimeSlot='" + startTimeSlot + '\'' +
                ", endTimeSlot='" + endTimeSlot + '\'' +
                '}';
    }
}
