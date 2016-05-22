package com.badmitrii.mine;

import static com.badmitrii.mine.MineFieldType.BOMB;
import static com.badmitrii.mine.MineFieldType.EMPTY;
import static com.badmitrii.mine.util.MineFieldParameters.BOMBS;
import static com.badmitrii.mine.util.MineFieldParameters.COLUMNS;
import static com.badmitrii.mine.util.MineFieldParameters.ROWS;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.badmitrii.util.Parameters;

public class MineFieldImplTest {
	private static final int ROWS_COUNT = 50;
	private static final int COLUMNS_COUNT = 50;
	private static final int BOMBS_COUNT = 100;
	
	private MineFieldImpl mineField;
	
	@Before
	public void init(){
		Parameters parameters = fieldParameters(ROWS_COUNT, COLUMNS_COUNT, BOMBS_COUNT);
		//We don't shuffle it deliberately
		mineField = new MineFieldImpl(parameters);
	}

	private Parameters fieldParameters(int rows, int cols, int bombs) {
		Parameters parameters = Parameters.empty();
		parameters.put(ROWS, rows);
		parameters.put(COLUMNS, cols);
		parameters.put(BOMBS, bombs);
		return parameters;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExcessiveBombs(){
		MineFieldImpl mfi = new MineFieldImpl(fieldParameters(ROWS_COUNT, COLUMNS_COUNT, ROWS_COUNT * COLUMNS_COUNT));
		print(mfi);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroBombs(){
		MineFieldImpl mfi = new MineFieldImpl(fieldParameters(ROWS_COUNT, COLUMNS_COUNT, 0));
		print(mfi);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeBombs(){
		MineFieldImpl mfi = new MineFieldImpl(fieldParameters(ROWS_COUNT, COLUMNS_COUNT, -10));
		print(mfi);
	}
	
	@Test
	public void testSuccesfulGet(){
		MineFieldType mftEmpty = mineField.get(ROWS_COUNT - 1, COLUMNS_COUNT - 1);
		Assert.assertEquals(mftEmpty, EMPTY);
		
		int row = BOMBS_COUNT / ( 2 * ROWS_COUNT );
		int column = COLUMNS_COUNT / 2;
		MineFieldType mftBomb = mineField.get(row, column);
		Assert.assertEquals(mftBomb, BOMB);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testPositiveOutOfBoundGet(){
		MineFieldType mft = mineField.get(ROWS_COUNT, COLUMNS_COUNT);
		print(mft);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeOutOfBoundGet(){
		MineFieldType mft = mineField.get(-1, -2);
		print(mft);
	}
	
	@Test
	public void testAdjacentCountSuccess(){
		IntegerValidator v = IntegerValidator.getInstance();
		
		int adjacentBombs = mineField.adjacentCount(0, 0, BOMB);
		int adjacentEmpty = mineField.adjacentCount(0, 0, EMPTY);
		
		@SuppressWarnings("unused")
		int adjacentBombsExpected = BOMBS_COUNT >= COLUMNS_COUNT + 2 ? 3 : 
										BOMBS_COUNT == COLUMNS_COUNT + 1 ? 2 :
											v.isInRange(BOMBS_COUNT, 2, COLUMNS_COUNT) ? 1 : 0;
		
		Assert.assertEquals(adjacentBombsExpected, adjacentBombs);
		Assert.assertEquals(3, adjacentBombs + adjacentEmpty);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeCoordinatesAdjacentCount(){
		int count = mineField.adjacentCount(-1, -1, BOMB);
		print(count);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testExcessiveCoordinatesAdjacentCount(){
		int count = mineField.adjacentCount(ROWS_COUNT, COLUMNS_COUNT, BOMB);
		print(count);
	}
	
	@Test
	public void iterateTest(){
		int[] bombCount = new int[1];
		mineField.iterate((x, y) -> {
			if(mineField.get(x, y) == BOMB)
				bombCount[0]++;
		});
		
		int emptyCount[] = new int[1];
		mineField.iterate((x, y) -> {
			if(mineField.get(x, y) == EMPTY)
				emptyCount[0]++;
		});
		
		Assert.assertEquals(BOMBS_COUNT, bombCount[0]);
		Assert.assertEquals(ROWS_COUNT * COLUMNS_COUNT - BOMBS_COUNT, emptyCount[0]);
	}
	
	@Test
	public void iterateEmpty(){
		int[] emptyCount = new int[1];
		mineField.iterateEmptyFields(ROWS_COUNT - 1, COLUMNS_COUNT - 1, (x, y) -> emptyCount[0]++);
		Assert.assertEquals(ROWS_COUNT * COLUMNS_COUNT - BOMBS_COUNT, emptyCount[0]);
		
		int[] countFromBeginning = new int[1];
		mineField.iterateEmptyFields(0, 0, (x, y) -> countFromBeginning[0]++);
		Assert.assertEquals(0, countFromBeginning[0]);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeCoordinateIterateEmpty(){
		int[] emptyCount = new int[1];
		mineField.iterateEmptyFields(- 1, - 1, (x, y) -> emptyCount[0]++);
	}
	
	private void print(Object o) {
		if(o.hashCode() == System.nanoTime())
			System.out.print(StringUtils.SPACE);
	}
}
