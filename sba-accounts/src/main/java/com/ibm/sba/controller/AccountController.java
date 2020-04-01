package com.ibm.sba.controller;

import com.ibm.sba.model.UserModel;
import com.ibm.sba.utils.EncrytedPasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibm.sba.entity.User;
import com.ibm.sba.model.HttpResponse;
import com.ibm.sba.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(description = "User Management Service")
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private UserService userService;

	/**
	 * Go through Zuul gateway, and should bypass authentication.
	 * @param user
	 * @return
	 */
	@PostMapping(value = "/register", produces = "application/json")
	@ApiOperation(value = "Account Register")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse> register(@ApiParam(name = "body", required = true) @RequestBody User user) {
		try {
			logger.debug("Entering register of Account service. param user is {}", user);
			user.setPassword(EncrytedPasswordUtils.encrytePassword(user.getPassword()));
			userService.addUser(user);
			HttpResponse response = new HttpResponse(HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<HttpResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Internal interface, consumed by auth service
	 * @param userName
	 * @return
	 */
	@GetMapping(value = "/findUser", produces = "application/json")
	@ApiOperation(value = "Find user by user name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse<User>> findUser(
				@RequestParam(value = "userName", required = true)
						String userName) {
		logger.debug("Entering findUser of Account service. param userName is {}", userName);

		try {
			User userInDB = userService.findUser(userName);
			if (userInDB != null) {
				HttpResponse<User> response = new HttpResponse(HttpStatus.OK.value(), "OK", userInDB);
				return new ResponseEntity<>(response, HttpStatus.OK);

			} else {
				HttpResponse response = new HttpResponse(HttpStatus.NOT_FOUND.value(), "Account not found");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Internal interface, consumed by other services
	 * @param userIdsString
	 * @return
	 */
	@GetMapping(value = "/getUsersByIds", produces = "application/json")
	@ApiOperation(value = "Get user list by given user id list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<HttpResponse<List<UserModel>>> getUsersByIds(
			@RequestParam(value = "userIds", required = false) String userIdsString) {
		logger.debug("Entering getUsersByIds of Account service. param userIds is {}", userIdsString);
		List<Long> userIds = new ArrayList<>();
		if(userIdsString == null || userIdsString.length() == 0){
			userIds.add(1L);
		}else{
			String[] idStrings = userIdsString.split(",");
			for(String idString: idStrings){
				if(idString.length() > 0){
					userIds.add(Long.valueOf(idString));
				}
			}
		}
		try {
			List<UserModel> users = userService.getUsersByIds(userIds);
			HttpResponse<List<UserModel>> response = new HttpResponse(HttpStatus.OK.value(), "OK", users);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception ex) {
			HttpResponse response = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
