package com.badmitrii.mine;

import java.util.function.BiConsumer;

/**
 * A mine field. In order to ensure uniform mine distribution, {@code shuffle()} must be called explicitly once an instance's created.
 * Implementation is not obligated to provide a constructor or/and static factory method to
 * produce a field that is already shuffled. 
 */
public interface MineField {

	/**
	 * @throws IndexOutOfBoundsException - if this field does not contain an item with a given coordinates.
	 */
	public void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc);
	
	public void iterate(BiConsumer<Integer, Integer> bc);
	
	/**
	 * @throws IndexOutOfBoundsException - if this field does not contain an item with a given coordinates.
	 */
	public int adjacentCount(int x, int y, MineFieldType mft);

	public MineField shuffle();
	
	/**
	 * @throws IndexOutOfBoundsException - if this field does not contain an item with a given coordinates.
	 */
	public MineFieldType get(int x, int y);
}
