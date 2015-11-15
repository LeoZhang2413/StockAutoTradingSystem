package com.aeolus.strategy;

public class NoNextCursorException extends RuntimeException{
	public NoNextCursorException(String msg){
		super(msg);
	}
}
