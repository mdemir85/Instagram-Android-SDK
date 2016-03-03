package com.melniqw.instagramsdk.exception;

import java.io.IOException;

public class WrongResponseCodeException extends IOException {

	public WrongResponseCodeException() {
		super();
	}

	public WrongResponseCodeException(String message) {
		super(message);
	}
}
