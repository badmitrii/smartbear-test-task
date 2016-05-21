package com.badmitrii.mine;

import java.util.function.BiConsumer;

public interface MineField {

	public void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc);
	
	public void iterate(BiConsumer<Integer, Integer> bc);
	
}
