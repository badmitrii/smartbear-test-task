package com.badmitrii.mvp.view.main;

import com.badmitrii.mine.util.BombType;

//Possible extension:

//It might be possible to extract something like
//base Fragment interface and associate any instance of Fragment with some view.
//Thus all communications between fragments within 
//the view would be managed by its presenter which would just subscribe 
//to events broadcasting by the fragment's underlying business-logic.

//By doing so, we would provide some fine-grained decoupling between the fragments
//themselves and the way they collaborate within a view.

//In my opinion this's definitely an overkill in the case of a simple minesweeper application.
public interface FieldFragment {
	
	public FieldFragment reset();
	
	public void showBomb(int x, int y, BombType bt);
	
	public void disable(int x, int y);
	
	public void showAdjacentMineCount(int x, int y, int adjacentMineCount);
}