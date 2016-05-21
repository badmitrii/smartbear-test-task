package com.badmitrii.mvp;

import com.badmitrii.util.Parameters;

public interface View {

	public void registerPresenter(Presenter presenter);
	
	public void show(Parameters parameters);
}
