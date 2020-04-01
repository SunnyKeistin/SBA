package com.ibm.sba.client;

import com.ibm.sba.model.MentorFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "fsd-course")
public interface CourseServiceClient {
    @GetMapping(value = "/course/api/v1/getAvailableCoursesBySkillAndTime")
    ResponseEntity<Object> getAvailableCoursesBySkillAndTime(
            @RequestParam(value = "skillId", required = true) Integer skillId,
            @RequestParam(value = "startTimeSlot", required = false) String startTimeSlot,
            @RequestParam(value = "endTimeSlot", required = false) String endTimeSlot);

    @GetMapping(value = "/course/api/v1/getCourseCommentsForMentors")
    ResponseEntity<Object> getCourseCommentsForMentors(
            @RequestParam(value = "mentorIds", required = false) String mentorIdsString);
}
