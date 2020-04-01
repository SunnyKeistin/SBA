package com.ibm.sba.controller;

import com.ibm.sba.entity.Course;
import com.ibm.sba.entity.CourseComment;
import com.ibm.sba.model.HttpResponse;
import com.ibm.sba.model.MentorFilter;
import com.ibm.sba.service.CourseService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(description = "Course Service")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    /**
     * Interface to get all skills, Go through Zuul gateway, and should bypass authentication.
     * @param
     * @return
     */
    @GetMapping(value = "/getAvailableCoursesBySkillAndTime", produces = "application/json")
    @ApiOperation(value = "Get available courses for specific time slot and skill")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "No Authroization"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Error") })
    public ResponseEntity<HttpResponse<List<Course>>> getAvailableCoursesBySkillAndTime(
            @RequestParam(value = "skillId", required = true) Integer skillId,
            @RequestParam(value = "startTimeSlot", required = false) String startTimeSlot,
            @RequestParam(value = "endTimeSlot", required = false) String endTimeSlot) {
        try {
            MentorFilter filter = new MentorFilter(skillId, startTimeSlot, endTimeSlot);
            logger.debug("entering getAvailableMentorIdsBySkillAndTime of Course service, param filter is {}", filter);
            HttpResponse<List<Course>> response =
                    new HttpResponse(HttpStatus.OK.value(), courseService.getAvailableCoursesBySkill(filter));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("failed to getAvailableCoursesBySkillAndTime", ex);
            HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Interface to get all skills, Go through Zuul gateway, and should bypass authentication.
     * @param
     * @return
     */
    @GetMapping(value = "/getCourseCommentsForMentors", produces = "application/json")
    @ApiOperation(value = "Get course comments for specific mentor ids")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "No Authroization"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Error") })
    public ResponseEntity<HttpResponse<List<CourseComment>>> getCourseCommentsForMentors(
            @RequestParam(value = "mentorIds", required = false) String mentorIdsString) {
        try {
            List<Long> mentorIds = new ArrayList<>();
            if(mentorIdsString == null || "".equals(mentorIdsString)){
                mentorIds.add(1L);
            }else{
                String[] idStrings = mentorIdsString.split(",");
                for(String idString: idStrings){
                    if(idString.length() > 0){
                        mentorIds.add(Long.valueOf(idString));
                    }
                }
            }

            logger.debug("entering getCourseCommentsForMentors of Course service, param mentorIds is {}", mentorIds);
            HttpResponse<List<CourseComment>> response =
                    new HttpResponse(HttpStatus.OK.value(), courseService.getCourseCommentsForMentors(mentorIds));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
