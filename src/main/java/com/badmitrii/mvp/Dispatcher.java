package com.badmitrii.mvp;

import com.badmitrii.util.Parameters;

//Since there's only one view in this application, 
//we don't really need in this kind of mediator.
//But if we had multi-view app, 
//we'd like to be able to dispatch from one view to another in a way
//that we could hide all dispatch-related details from presenters.
public interface Dispatcher {

	public <T extends Presenter> void dispatch(Class<T> presenterClass, Parameters parameters);
}
