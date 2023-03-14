package model;

public class MeetingTime {
    String meetingTimeID;
    String meetingTime;

    public MeetingTime(String meetingTimeID, String meetingTime) {
        this.meetingTimeID = meetingTimeID;
        this.meetingTime = meetingTime;
    }

    public String getMeetingTimeID() {
        return meetingTimeID;
    }

    public String getMeetingTime() {
        return meetingTime;
    }
}
