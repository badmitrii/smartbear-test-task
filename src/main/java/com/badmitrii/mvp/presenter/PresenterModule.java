package com.badmitrii.mvp.presenter;

import com.google.inject.AbstractModule;

public class PresenterModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(MainPresenter.class).to(MainPresenterImpl.class);
	}

}
