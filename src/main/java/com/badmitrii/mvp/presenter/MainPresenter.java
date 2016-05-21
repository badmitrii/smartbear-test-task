package com.badmitrii.mvp.presenter;

import com.badmitrii.mvp.Presenter;
import com.badmitrii.util.Parameters;

public interface MainPresenter extends Presenter{
	
	public void handleClick(int x, int y);
	
	public void reset();
	
	public void newGame(Parameters parameters);
}
