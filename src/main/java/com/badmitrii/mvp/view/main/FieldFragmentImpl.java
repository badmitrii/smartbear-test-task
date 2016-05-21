package com.badmitrii.mvp.view.main;

import static com.badmitrii.mine.util.MineFieldParameters.COLUMNS;
import static com.badmitrii.mine.util.MineFieldParameters.ROWS;

import java.awt.Component;
import java.util.Arrays;
import java.util.function.BiConsumer;

import javax.swing.JPanel;

import com.badmitrii.mine.util.BombType;
import com.badmitrii.util.Parameters;


class FieldFragmentImpl implements FieldFragment{
	
	private final JPanel container = new JPanel();
	private MineFieldItem[][] items;
	
	@Override
	public FieldFragmentImpl reset() {
		Arrays.stream(items)
				.flatMap(arr -> Arrays.stream(arr))
				.forEach(mfi -> mfi.reset());
		return this;
	}
	
	@Override
	public FieldFragmentImpl reset(Parameters parameters){
		container.removeAll();
		for(int i = 0; i < parameters.get(ROWS); i++){
			for(int j = 0; j < parameters.get(COLUMNS); j++){
				MineFieldItem item = new MineFieldItem();
				items[i][j] = new MineFieldItem();
				container.add(item.asComponent());
			}
		}
		return this;
	}
	
	@Override
	public void showBomb(int x, int y, BombType bt){
		items[x][y].setBomb(bt);
	}
	
	@Override
	public void showAdjacentMineCount(int x, int y, int adjacentMineCount){
		items[x][y].setAdjacentMine(adjacentMineCount);
	}
	
	public void setClickListener(BiConsumer<Integer, Integer> bc){
		for(int i = 0; i < items.length; i++){
			for(int j = 0; j < items.length; j++){
				int x = i,
					y = j;
				items[i][j].setActionListener(() -> bc.accept(x, y)); 
			}
		}
	}
	
	public Component asComponent(){
		return container;
	}
}