package com.ibm.sba.service;

import com.ibm.sba.client.AccountServiceClient;
import com.ibm.sba.client.CourseServiceClient;
import com.ibm.sba.entity.Course;
import com.ibm.sba.entity.CourseComment;
import com.ibm.sba.entity.Mentor;
import com.ibm.sba.entity.MentorSkill;
import com.ibm.sba.mapper.MentorMapper;
import com.ibm.sba.model.MentorFilter;
import com.ibm.sba.model.UserModel;
import com.ibm.sba.utils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class MentorService {
	private static final Logger logger = LoggerFactory.getLogger(MentorService.class);
	
	@Autowired
	private MentorMapper mentorMapper;

	@Autowired
	private AccountServiceClient accountClient;

	@Autowired
	private CourseServiceClient courseClient;

	public void addMentorSkill(MentorSkill mentorSkill) {
		logger.debug("entering addMentorSkill.");
		mentorMapper.saveMentorSkill(mentorSkill);

	}

	public void deleteMentorSkill(MentorSkill mentorSkill) {
		logger.debug("entering deleteMentorSkill.");
		mentorMapper.deleteMentorSkill(mentorSkill);
	}

	public void clearMentorSkills(Long mentorId) {
		logger.debug("entering clearMentorSkills.");
		mentorMapper.deleteMentorSkills(mentorId);
	}

	public List<MentorSkill> getMentorSkills(Long mentorId){
		logger.debug("entering getMentorSkills.");
		List<MentorSkill> mentorSkills = mentorMapper.getMentorSkills(mentorId);
		logger.debug("mentorSkills {} returned from db.", mentorSkills);
		return mentorSkills;
	}

	public List<Mentor> getMentorsBySkillAndTimeSlot(MentorFilter filter){
		logger.debug("entering getMentorsBySkillAndTimeSlot.");
		List<Mentor> mentors = new ArrayList<>();

		Map<Long, Mentor> mentorsMap = new HashMap<>();

		Set<Long> mentorIds = new HashSet<>();

		ResponseEntity<Object> response = courseClient.getAvailableCoursesBySkillAndTime(
				filter.getSkillId(), filter.getStartTimeSlot(), filter.getEndTimeSlot());
		JsonObject jsonResponse = JSONUtils.getResult(response);
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<Course>>(){}.getType();
		List<Course> availableCourses = gson.fromJson(jsonResponse.get("data").toString(), listType);
		availableCourses.stream().forEach( course -> {
			mentorIds.add(course.getMentorId());
		});

		StringBuilder sb = new StringBuilder();
		mentorIds.stream().forEach( mentorId -> {
			sb.append(mentorId);
			sb.append(",");
		});

		// get user information by ids
		response = accountClient.getUsersByIds(sb.toString());
		jsonResponse = JSONUtils.getResult(response);
		gson = new Gson();
		listType = new TypeToken<ArrayList<UserModel>>(){}.getType();

		List<UserModel> users = gson.fromJson(jsonResponse.get("data").toString(), listType);

		users.stream().forEach( user -> {
			Mentor mentor = new Mentor(user);
			// get skills for mentor
			mentor.setSkills(mentorMapper.getMentorSkills(mentor.getMentorId()));
			mentorsMap.put(user.getId(), mentor);
		});


		// add available courses for mentor
		availableCourses.stream().forEach( course -> {
			Mentor mentor = mentorsMap.get(course.getMentorId());
			if(mentor.getCourses() == null){
				mentor.setCourses(new ArrayList<>());
			}
			mentor.getCourses().add(course);
		});


		// get comments by mentor ids
		response = courseClient.getCourseCommentsForMentors(sb.toString());
		jsonResponse = JSONUtils.getResult(response);
		gson = new Gson();
		listType = new TypeToken<ArrayList<CourseComment>>(){}.getType();
		List<CourseComment> comments = gson.fromJson(jsonResponse.get("data").toString(), listType);

		comments.stream().forEach( comment -> {
			Mentor mentor = mentorsMap.get(comment.getMentorId());
			if(mentor.getComments() == null){
				mentor.setComments(new ArrayList<>());
			}
			mentor.getComments().add(comment);
		});


		mentors.addAll(mentorsMap.values());
		logger.debug("mentors {} returned from db.", mentors);
		return mentors;
	}
}
