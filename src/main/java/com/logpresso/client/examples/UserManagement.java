package com.logpresso.client.examples;

import java.io.IOException;

import com.logpresso.client.Logpresso;
import com.logpresso.client.User;

public class UserManagement {
	public static void main(String[] args) throws IOException {
		new UserManagement().run();
	}

	public void run() throws IOException {
		Logpresso logpresso = new Logpresso();
		try {
			logpresso.connect("localhost", "root", "logpresso");
			logpresso.createUser(newUser());
			// logpresso.updateUser(newUser());
			// logpresso.removeUser("xeraph");
		} finally {
			logpresso.close();
		}
	}

	private User newUser() {
		User user = new User();

		// required
		user.setLoginName("xeraph");
		user.setName("Yang, BongYeol");

		// optional for update
		user.setPassword("Password_0");

		// optional
		user.setDescription("Logpresso Product Manager");
		user.setDepartment("R&D");
		user.setTitle("Director");
		user.setEmail("xeraph@eediom.com");
		user.setLang("ko"); // or "en", "zh", "ja"
		user.setRole("admin"); // or "member"
		user.setPhone("+82-10-9031-0453");
		return user;
	}
}
