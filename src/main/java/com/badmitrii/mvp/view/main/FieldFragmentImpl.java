package com.badmitrii.mvp.view.main;

import static com.badmitrii.mine.util.MineFieldParameters.COLUMNS;
import static com.badmitrii.mine.util.MineFieldParameters.ROWS;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.function.BiConsumer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.badmitrii.mine.util.BombType;
import com.badmitrii.util.Parameters;

class FieldFragmentImpl implements FieldFragment{
	
	private MineFieldItem[][] items;
	private BiConsumer<Integer, Integer> clickListener;
	
	@Override
	public FieldFragmentImpl reset() {
		Arrays.stream(items)
				.flatMap(arr -> Arrays.stream(arr))
				.forEach(mfi -> mfi.reset());
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
		this.clickListener = bc;
	}
	
	public Component asComponent(Parameters parameters){
		JPanel retVal = new JPanel(); 
		retVal.setLayout(new BoxLayout(retVal, BoxLayout.Y_AXIS));
		items = new MineFieldItem[parameters.get(ROWS)][parameters.get(COLUMNS)];
		for(int i = 0; i < parameters.get(ROWS); i++){
			FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 0, 0);
			JPanel rowPanel = new JPanel(fl);
			for(int j = 0; j < parameters.get(COLUMNS); j++){
				items[i][j] = new MineFieldItem();
				int x = i,
					y = j;
				items[i][j].setActionListener(() -> clickListener.accept(x, y)); 
				rowPanel.add(items[i][j].asComponent());
			}
			retVal.add(rowPanel);
		}
		return retVal;
	}
}