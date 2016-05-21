package com.badmitrii.mvp.view.main;

import static com.badmitrii.mine.util.MineFieldParameters.COLUMNS;
import static com.badmitrii.mine.util.MineFieldParameters.ROWS;

import javax.swing.JPanel;

import com.badmitrii.mine.util.BombType;
import com.badmitrii.util.Parameters;


class FieldFragmentImpl implements FieldFragment{
	
	private final JPanel container = new JPanel();
	private MineFieldItem[][] items;
	
	@Override
	public void reset(Parameters parameters){
		container.removeAll();
		for(int i = 0; i < parameters.get(ROWS); i++){
			for(int j = 0; j < parameters.get(COLUMNS); j++){
				MineFieldItem item = new MineFieldItem();
				items[i][j] = new MineFieldItem();
				container.add(item.asComponent());
			}
		}
	}
	
	@Override
	public void showBomb(int x, int y, BombType bt){
		items[x][y].setBomb(bt);
	}
	
	@Override
	public void showAdjacentMineCount(int x, int y, int adjacentMineCount){
		items[x][y].setAdjacentMine(adjacentMineCount);
	}
}