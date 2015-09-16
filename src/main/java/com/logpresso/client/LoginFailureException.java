package com.logpresso.client;

import java.io.IOException;

public class LoginFailureException extends IOException {
	private static final long serialVersionUID = 1L;
	private String errorCode;

	public LoginFailureException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public String errorCode() {
		return errorCode;
	}
}
