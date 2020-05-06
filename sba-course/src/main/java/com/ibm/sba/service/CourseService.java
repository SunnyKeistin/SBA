package com.ibm.sba.service;

import com.ibm.sba.entity.Course;
import com.ibm.sba.entity.CourseComment;
import com.ibm.sba.mapper.CourseMapper;
import com.ibm.sba.model.MentorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.validation.Valid;

@Service
public class CourseService {

    @Autowired
    private CourseMapper mapper;

    public List<Course> getAvailableCoursesBySkill(MentorFilter filter) {
        return mapper.getAvailableCoursesBySkill(filter);
    }

    public List<CourseComment> getCourseCommentsForMentors(List<Long> mentorIds) {
        return mapper.getCourseCommentsForMentors(mentorIds);
    }
    
    public void addCourseRequest(@Valid Course course) throws Exception{
		List<Course> courseListMentor = mapper.findAllByMentor(course.getMentorId());
		List<Course> courseListUser = mapper.findAllByUser(course.getUserId());
		for(Course existingTraining:courseListMentor){
			if(existingTraining.getStatus().equals("Approved")){
				if(course.getStartDate().after(existingTraining.getStartDate()) || course.getStartDate().equals(existingTraining.getStartDate())){
					if(course.getStartDate().before(existingTraining.getEndDate()) || course.getStartDate().equals(existingTraining.getEndDate())){
						throw new Exception("Start Date Coincides with an existing approved training of Mentor");
					}
				}
				else if(course.getEndDate().after(existingTraining.getStartDate()) || course.getEndDate().equals(existingTraining.getStartDate())){
					if(course.getEndDate().before(existingTraining.getEndDate()) || course.getEndDate().equals(existingTraining.getEndDate())){
						throw new Exception("End Date Coincides with an existing approved training of Mentor");
					}
				}
			}
		}
		for(Course existingCourse:courseListUser){
			if(existingCourse.getStatus().equals("Approved")){
				if(course.getStartDate().after(existingCourse.getStartDate()) || course.getStartDate().equals(existingCourse.getStartDate())){
					if(course.getStartDate().before(existingCourse.getEndDate()) || course.getStartDate().equals(existingCourse.getEndDate())){
						throw new Exception("Start Date Coincides with an existing approved training of User");
					}
				}
				else if(course.getEndDate().after(existingCourse.getStartDate()) || course.getEndDate().equals(existingCourse.getStartDate())){
					if(course.getEndDate().before(existingCourse.getEndDate()) || course.getEndDate().equals(existingCourse.getEndDate())){
						throw new Exception("End Date Coincides with an existing approved training of User");
					}
				}
			}
		}
		mapper.save(course);
	}
	
	public void editStatus(@Valid Course course) {
		mapper.save(course);
	}
}
