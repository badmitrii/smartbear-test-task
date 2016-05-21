package com.badmitrii.mine;

import java.util.function.BiConsumer;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import com.google.inject.assistedinject.Assisted;

class MineFieldImpl implements MineField{
	
	private final MineFieldType[][] field;
	
	@Inject
	public MineFieldImpl(@Assisted("rows") int rows, @Assisted("cols") int columns, @Assisted("bomb") int bombs) {
		if(rows <= 0) throw new IllegalArgumentException("rows must be postive: " + rows);
		if(columns <= 0) throw new IllegalArgumentException("columns must be postive: " + columns);
		Validate.exclusiveBetween(0, columns * rows, bombs, String.format("Bombs count must be an interval (%s, %s). Bombs: %s", 
															0, 
															rows * columns, 
															bombs));
		field = new MineFieldType[rows][columns];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				if(i * rows + j < bombs) field[i][j] = MineFieldType.BOMB;
				else field[i][j] = MineFieldType.EMPTY;
			}
		}
		shuffle(field);
	}

	public void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc){
		
	}
	
	public void iterate(BiConsumer<Integer, Integer> bc){
		
	}
	
	private static void shuffle(MineFieldType[][] field) {
		for(int i = 0; i < field.length; i++){
			for(int j = 0; j < field[i].length; j++){
				
			}
		}
	}
}
