package com.badmitrii.mine;

import com.google.inject.assistedinject.Assisted;

public interface MineFieldFactory {
	
	public MineField create(@Assisted("rows") int rows, @Assisted("cols") int columns, @Assisted("bomb") int bombs);
}
