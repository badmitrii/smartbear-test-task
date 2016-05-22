package com.badmitrii.mvp.view.main;

import com.google.inject.AbstractModule;

public class MainViewModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(MainView.class).to(MainViewImpl.class);
	}
}
