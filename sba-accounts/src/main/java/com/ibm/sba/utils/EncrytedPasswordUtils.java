package com.ibm.sba.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class EncrytedPasswordUtils {

	public static String encrytePassword(String password) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder.encode(password);
	}

	public static boolean matchPassword(String password, String hashpassword) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder.matches(password, hashpassword);
	}
	
	public static void main(String[] args) {
		//System.out.println(matchPassword("zaq12wsx", "{bcrypt}$2a$10$bv6Wp/CDFrhI6adHwfGY5ebL8GfCVe8HwlrR3CFpYuKtLw96ZnJZ."));

	}

}
