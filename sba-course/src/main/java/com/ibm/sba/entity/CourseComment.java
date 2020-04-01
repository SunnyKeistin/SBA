package com.ibm.sba.entity;

import java.util.Date;

public class CourseComment {
    private Long courseId;
    private Long userId;
    private Long mentorId;
    private Date commentDate;
    private String comment;
    private Integer rating;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "CourseComment{" +
                "courseId=" + courseId +
                ", userId=" + userId +
                ", mentorId=" + mentorId +
                ", commentDate=" + commentDate +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
