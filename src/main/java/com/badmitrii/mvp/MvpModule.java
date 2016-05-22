package com.badmitrii.mvp;

import com.google.inject.AbstractModule;

public class MvpModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(Dispatcher.class).to(DispatcherImpl.class);
	}
}
