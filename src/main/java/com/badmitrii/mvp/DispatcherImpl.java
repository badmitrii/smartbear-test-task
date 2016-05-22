package com.badmitrii.mvp;

import javax.inject.Inject;

import com.badmitrii.util.Parameters;
import com.google.inject.Injector;

class DispatcherImpl implements Dispatcher{
	
	private final Injector injector;
	
	@Inject
	DispatcherImpl(Injector injector) {
		this.injector = injector;
	}

	@Override
	public <T extends Presenter> void dispatch(Class<T> presenterClass, Parameters parameters) {
		injector.getInstance(presenterClass).start(parameters);
	}
}
