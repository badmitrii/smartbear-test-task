package com.badmitrii.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badmitrii.mine.util.GameUtils;
import com.badmitrii.mvp.Dispatcher;
import com.badmitrii.mvp.presenter.MainPresenter;
import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

class ApplicationImpl implements Application{
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationImpl.class);
	
	private final Injector injector;
	
	private static class Holder {
		private static final ApplicationImpl INSTANCE = new ApplicationImpl();
	}
	
	static ApplicationImpl get(){
		return Holder.INSTANCE;
	}
	
	private ApplicationImpl() {	
		injector = LifecycleInjector.builder()
									.withModules(new ApplicationModule())
									.build()
									.createInjector();
		LifecycleManager manager = injector.getInstance(LifecycleManager.class);
		try {
			manager.start();
		} catch (Exception e) {
			LOGGER.error("Application terminated due to an exception thrown", e);
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		injector.getInstance(Dispatcher.class).dispatch(MainPresenter.class, GameUtils.easyParameters());
	}
}
