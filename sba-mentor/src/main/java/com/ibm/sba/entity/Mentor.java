package com.ibm.sba.entity;

import com.ibm.sba.model.UserModel;

import java.util.List;

public class Mentor {

    public Mentor() {

    }

    public Mentor(UserModel userModel) {
        this.mentorId = userModel.getId();
        this.avatarPath = userModel.getAvatarPath();
        this.email = userModel.getEmail();
        this.firstName = userModel.getFirstName();
        this.mentorId = userModel.getId();
        this.lastName = userModel.getLastName();
        this.mobile = userModel.getMobile();
    }

    private Long mentorId;

    private String email;

    private String firstName;

    private String lastName;

    private String mobile;

    private String avatarPath;

    private Float averageRating;

    private List<Course> courses;

    private List<MentorSkill> skills;

    private List<CourseComment> comments;

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<MentorSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<MentorSkill> skills) {
        this.skills = skills;
    }

    public List<CourseComment> getComments() {
        return comments;
    }

    public void setComments(List<CourseComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Mentor{" +
                "mentorId=" + mentorId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", averageRating=" + averageRating +
                ", courses=" + courses +
                ", skills=" + skills +
                '}';
    }
}
