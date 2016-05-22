package com.badmitrii.mine;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class MineModule extends AbstractModule{

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder()
	     .implement(MineField.class, MineFieldImpl.class)
	     .build(MineFieldFactory.class));
	}
}
