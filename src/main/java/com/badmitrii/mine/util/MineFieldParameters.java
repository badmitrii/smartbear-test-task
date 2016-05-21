package com.badmitrii.mine.util;

import com.badmitrii.util.ParameterMetaData;

public class MineFieldParameters<T> implements ParameterMetaData<T> {
	public static final MineFieldParameters<Integer> ROWS = new MineFieldParameters<>();
	public static final MineFieldParameters<Integer> COLUMNS = new MineFieldParameters<>();
	public static final MineFieldParameters<Integer> BOMBS = new MineFieldParameters<>();
}
