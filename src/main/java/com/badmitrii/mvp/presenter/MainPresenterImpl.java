package com.badmitrii.mvp.presenter;

import javax.inject.Inject;

import static com.badmitrii.mine.util.MineFieldParameters.*;

import com.badmitrii.mine.MineField;
import com.badmitrii.mine.MineFieldFactory;
import com.badmitrii.mvp.view.main.MainView;
import com.badmitrii.util.Parameters;

class MainPresenterImpl implements MainPresenter{
	
	private final MainView mainView;
	private final MineFieldFactory mineFieldFactory;
	private MineField mineField;
	private int openedFieldCount;
	private int emptyFieldCount;
	
	@Inject
	MainPresenterImpl(MainView mainView, MineFieldFactory mineFieldFactory) {
		this.mainView = mainView;
		this.mineFieldFactory = mineFieldFactory;
	}

	@Override
	public void start(Parameters parameters) {
		mineField = mineFieldFactory.create(parameters.get(ROWS), parameters.get(COLUMNS), parameters.get(BOMBS));
		mainView.show(parameters);
	}

	@Override
	public void handleClick(int x, int y) {
		
	}

	@Override
	public void reset() {
		mainView.field().reset();
		mineField.shuffle();
	}

	@Override
	public void newGame(Parameters parameters) {
		todo
	}
}
