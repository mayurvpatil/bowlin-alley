package com.bowling.exception;

import java.util.Arrays;

public class InvalidArgumentException extends RuntimeException {

	private static final long serialVersionUID = -4020148900700787680L;

	InvalidArgumentException(String msg) {
		super(msg);
	}

	@Override
	public String toString() {
		return "InvalidArgumentException [getMessage()=" + getMessage() + ", getLocalizedMessage()="
				+ getLocalizedMessage() + ", getCause()=" + getCause() + ", toString()=" + super.toString()
				+ ", fillInStackTrace()=" + fillInStackTrace() + ", getStackTrace()=" + Arrays.toString(getStackTrace())
				+ ", getSuppressed()=" + Arrays.toString(getSuppressed()) + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}