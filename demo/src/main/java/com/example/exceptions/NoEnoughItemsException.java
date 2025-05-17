package com.example.exceptions;

public class NoEnoughItemsException extends Exception {
	public NoEnoughItemsException(String errorMessage) {
        super(errorMessage);
    }
}
