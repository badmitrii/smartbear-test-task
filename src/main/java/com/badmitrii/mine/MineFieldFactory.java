package com.badmitrii.mine;

import com.badmitrii.util.Parameters;
import com.google.inject.assistedinject.Assisted;

public interface MineFieldFactory {
	
	public MineField create(@Assisted Parameters parameters);
}
