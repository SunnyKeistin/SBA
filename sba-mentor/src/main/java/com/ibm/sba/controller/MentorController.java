package com.ibm.sba.controller;

import com.ibm.sba.entity.Mentor;
import com.ibm.sba.entity.MentorSkill;
import com.ibm.sba.entity.Skill;
import com.ibm.sba.model.HttpResponse;
import com.ibm.sba.model.MentorFilter;
import com.ibm.sba.service.MentorService;
import com.ibm.sba.service.SkillService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(description = "Mentor Service")
public class MentorController {
	private static final Logger logger = LoggerFactory.getLogger(MentorController.class);

	@Autowired
	private SkillService skillService;
	@Autowired
	private MentorService mentorService;

	/**
	 * Interface to get all skills, Go through Zuul gateway, and should bypass authentication.
	 * @param
	 * @return
	 */
	@GetMapping(value = "/skills", produces = "application/json")
	@ApiOperation(value = "Get all skills information")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse<List<Skill>>> getSkills() {
		try {
			logger.debug("entering getSkills of Mentor service.");
			HttpResponse<List<Skill>> response =
						new HttpResponse(HttpStatus.OK.value(), skillService.getSkills());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Interface for mentor to add specific skill, Go through Zuul gateway, should be authenticated.
	 * @param mentorSkill
	 * @return
	 */
	@PostMapping(value = "/addMentorSkill", produces = "application/json")
	@ApiOperation(value = "Mentor adds specific skill")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse> addMentorSkill(
				@ApiParam(name = "body", required = true) @RequestBody MentorSkill mentorSkill
	) {
		try {
			logger.debug("entering addMentorSkill of Mentor service. param mentorSkill is {}", mentorSkill);
			mentorService.addMentorSkill(mentorSkill);
			HttpResponse response = new HttpResponse(HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Interface for mentor to delete specific skill, Go through Zuul gateway, should be authenticated.
	 * @param mentorSkill
	 * @return
	 */
	@DeleteMapping(value = "/deleteMentorSkill", produces = "application/json")
	@ApiOperation(value = "Mentor delete specific skill")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse> deleteMentorSkill(
			@ApiParam(name = "body", required = true) @RequestBody MentorSkill mentorSkill
	) {
		try {
			logger.debug("entering deleteMentorSkill of Mentor service. param mentorSkill is {}", mentorSkill);
			mentorService.deleteMentorSkill(mentorSkill);
			HttpResponse response = new HttpResponse(HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Interface for mentor to delete all their own skills, Go through Zuul gateway, should be authenticated.
	 * @param mentorId
	 * @return
	 */
	@DeleteMapping(value = "/clearMentorSkills", produces = "application/json")
	@ApiOperation(value = "Mentor deletes multiple skills")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse> clearMentorSkills(
			@RequestParam(value = "mentorId", required = true) Long mentorId
	) {
		try {
			logger.debug("entering clearMentorSkills of Mentor service. param mentorId is {}", mentorId);
			mentorService.clearMentorSkills(mentorId);
			HttpResponse response = new HttpResponse(HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Internal interface for getting specific mentor's skills, and should bypass authentication.
	 * @param mentorId
	 * @return
	 */
	@GetMapping(value = "/getMentorSkills", produces = "application/json")
	@ApiOperation(value = "Get all skills for specific mentor")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse<List<MentorSkill>>> getMentorSkills(
			@RequestParam(value = "mentorId", required = true) Long mentorId) {
		try {
			logger.debug("entering getMentorSkills of Mentor service. param mentorId is {}", mentorId);
			HttpResponse<List<MentorSkill>> response =
					new HttpResponse(HttpStatus.OK.value(), mentorService.getMentorSkills(mentorId));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Internal interface for getting specific mentor's skills, and should bypass authentication.
	 * @param filter
	 * @return
	 */
	@GetMapping(value = "/getMentorsByFilter", produces = "application/json")
	@ApiOperation(value = "Get all skills for specific mentor")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse<List<Mentor>>> getMentorsByFilter(
			@ApiParam(name = "body", required = true) @RequestBody MentorFilter filter) {
		try {
			logger.debug("entering getMentorsByFilter of Mentor service. param filter is {}", filter);
			HttpResponse<List<Mentor>> response =
					new HttpResponse(HttpStatus.OK.value(), mentorService.getMentorsBySkillAndTimeSlot(filter));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
