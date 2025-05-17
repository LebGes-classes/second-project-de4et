package com.example.exceptions;

public class NoEnoughSpaceException extends Exception {
	public NoEnoughSpaceException(String errorMessage) {
        super(errorMessage);
    }
}
