package com.ibm.sba.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.sba.entity.User;
import com.ibm.sba.model.HttpResponse;
import com.ibm.sba.model.UserModel;
import com.ibm.sba.service.UserService;
import com.ibm.sba.utils.EncrytedPasswordUtils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(description = "User Management Service")
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private UserService userService;
	
	@GetMapping("/authenticate")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		logger.info("start");

		Map<String, String> map = new HashMap<String, String>();
		map.put("token", generateJwt(getUser(authHeader)));

		String username = getUser(authHeader);
		map.put("username", username);
		
		User user = userService.findUser(username);
		String firstname = user.getFirstName();
		String lastname = user.getLastName();
		String role = user.getRole();
		map.put("role", role);
		map.put("firstname", firstname);
		map.put("lastname", lastname);
		logger.info("END OF AUTH FUNCTION");
		return map;
	}

	private String getUser(String authHeader) {
		String user = new String(Base64.getDecoder().decode(authHeader.substring(6)));
		user = user.substring(0, user.indexOf(":"));
		logger.info(user);
		return user;
	}

	private String generateJwt(String user) {
		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);

		builder.setIssuedAt(new Date());

		builder.setExpiration(new Date((new Date()).getTime() + 1200000));
		builder.signWith(SignatureAlgorithm.HS256, "secretkey");

		String token = builder.compact();
		logger.info(token);
		return token;
	}

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
