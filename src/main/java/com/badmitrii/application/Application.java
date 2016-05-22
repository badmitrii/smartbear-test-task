package com.badmitrii.application;

public interface Application {

	public static Application getInstance(){
		return ApplicationImpl.get();
	}
	
	public void run();
	
}
