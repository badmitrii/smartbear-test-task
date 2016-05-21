package com.badmitrii.mvp.view.main;

import com.badmitrii.util.Parameters;

public interface FieldFragment {
	
	public void reset(Parameters parameters);
	
	public void showBomb(int x, int y);
	
	public void showEmpty(int x, int y);
	
	public void showExploded(int x, int y);
}
