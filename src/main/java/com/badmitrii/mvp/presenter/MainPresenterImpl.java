package com.badmitrii.mvp.presenter;

import static com.badmitrii.mine.MineFieldType.BOMB;
import static com.badmitrii.mine.MineFieldType.EMPTY;
import static com.badmitrii.mine.util.BombType.EXPLODED_BOMB_APPEARANCE;
import static com.badmitrii.mine.util.BombType.SIMPLE_BOMB_APPEARANCE;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.badmitrii.mine.MineField;
import com.badmitrii.mine.MineFieldFactory;
import com.badmitrii.mine.MineFieldType;
import com.badmitrii.mvp.view.main.MainView;
import com.badmitrii.util.Parameters;

@Singleton
class MainPresenterImpl implements MainPresenter{

	private Parameters currentGameParameters;
	private final MainView mainView;
	private final MineFieldFactory mineFieldFactory;
	private MineField mineField;
	private int openedFieldCount;
	private int emptyFieldCount;
	
	private final BiConsumer<Integer, Integer> incrementEmpty = (x, y) -> {
		if(mineField.get(x, y) == EMPTY)
			emptyFieldCount++;
	};
	
	private Map<MineFieldType, BiConsumer<Integer, Integer>> handlers;
	
	@Inject
	MainPresenterImpl(MainView mainView, MineFieldFactory mineFieldFactory) {
		this.mainView = mainView;
		this.mineFieldFactory = mineFieldFactory;
		attachHandlers();
	}
	
	private void attachHandlers(){
		handlers = new HashMap<>();
		
		BiConsumer<Integer, Integer> showMine = (i, j) -> {
			if(mineField.get(i, j) == BOMB)
				mainView.field().showBomb(i, j, SIMPLE_BOMB_APPEARANCE);
		};
		
		BiConsumer<Integer, Integer> disable = (i, j) -> {
			mainView.field().disable(i, j);
		};
		
		handlers.put(BOMB, (x, y) -> {
			mineField.iterate(showMine);
			mineField.iterate(disable);
			mainView.field().showBomb(x, y, EXPLODED_BOMB_APPEARANCE);
		});
		
		handlers.put(EMPTY, (x, y) -> {
			mineField.iterateEmptyFields(x, y, (i, j) -> {
				mainView.field().showAdjacentMineCount(i, j, mineField.adjacentCount(i, j, BOMB));
				if(++openedFieldCount == emptyFieldCount){
					mineField.iterate(showMine);
					mineField.iterate(disable);
				}
			});
		});
	}

	
	@PostConstruct
	public void init(){
		mainView.registerPresenter(this);
	}

	@Override
	public void start(Parameters parameters) {
		currentGameParameters = parameters;
		mineField = mineFieldFactory.create(parameters).shuffle();
		mineField.iterate(incrementEmpty);
		mainView.show(parameters);
	}

	@Override
	public void handleClick(int x, int y) {
		handlers.get(mineField.get(x, y)).accept(x, y);
	}

	@Override
	public void reset() {
		mainView.field().reset();
		mineField = mineFieldFactory.create(currentGameParameters).shuffle();
		openedFieldCount = 0;
	}

	@Override
	public void newGame(Parameters parameters) {
		currentGameParameters = parameters;
		mineField = mineFieldFactory.create(parameters).shuffle();
		openedFieldCount = 0;
		emptyFieldCount = 0;
		mineField.iterate(incrementEmpty);
		mainView.show(parameters);
	}
}
