package com.badmitrii.mvp.presenter;

import static com.badmitrii.mine.MineFieldType.*;
import static com.badmitrii.mine.util.BombType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.badmitrii.mine.MineField;
import com.badmitrii.mine.MineFieldFactory;
import com.badmitrii.mine.MineFieldType;
import com.badmitrii.mine.util.BombType;
import com.badmitrii.mvp.view.main.MainView;
import com.badmitrii.util.Parameters;

class MainPresenterImpl implements MainPresenter{
	
	private final MainView mainView;
	private final MineFieldFactory mineFieldFactory;
	private MineField mineField;
	private int openedFieldCount;
	private int emptyFieldCount;
	
	private Map<MineFieldType, BiConsumer<Integer, Integer>> handlers;
	{
		handlers = new HashMap<>();
		
		handlers.put(BOMB, (x, y) -> {
			mineField.iterate((i, j) -> {
				if(mineField.get(i, j) == BOMB)
					mainView.field().showBomb(i, j, SIMPLE_BOMB);
			});
			mainView.field().showBomb(x, y, EXPLODED_BOMB);
		});
		
		handlers.put(EMPTY, (x, y) -> {
			mineField.iterateEmptyFields(x, y, (i, j) -> mainView.field()
																.showAdjacentMineCount(i, j, mineField.adjacentCount(i, j, BOMB)));
		});
	}
	
	@Inject
	MainPresenterImpl(MainView mainView, MineFieldFactory mineFieldFactory) {
		this.mainView = mainView;
		this.mineFieldFactory = mineFieldFactory;
	}
	
	@PostConstruct
	public void init(){
		mainView.registerPresenter(this);
	}

	@Override
	public void start(Parameters parameters) {
		mineField = mineFieldFactory.create(parameters);
		mainView.show(parameters);
	}

	@Override
	public void handleClick(int x, int y) {
		if(mineField.get(x, y) == BOMB){
			
		} else if()
	}

	@Override
	public void reset() {
		mainView.field().reset();
		mineField.shuffle();
		openedFieldCount = 0;
	}

	@Override
	public void newGame(Parameters parameters) {
		mineField = mineFieldFactory.create(parameters);
		mainView.field().reset(parameters);
		openedFieldCount = 0;
		emptyFieldCount = 0;
		mineField.iterate((x, y) -> {
			if(mineField.get(x, y) == EMPTY)
				emptyFieldCount++;
		});
	}
}
