package com.badmitrii.application;

import com.badmitrii.mine.MineModule;
import com.badmitrii.mvp.presenter.PresenterModule;
import com.badmitrii.mvp.view.main.MainViewModule;
import com.google.inject.AbstractModule;

class ApplicationModule extends AbstractModule{

	@Override
	protected void configure() {
		install(new MineModule());
		install(new PresenterModule());
		install(new MainViewModule());
	}
}
