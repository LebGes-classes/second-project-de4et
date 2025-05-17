package com.example.exceptions;

public class NoEnoughMoneyException extends Exception {
	public NoEnoughMoneyException(String errorMessage) {
        super(errorMessage);
    }
}