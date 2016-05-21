package com.badmitrii.mine;

import java.util.Random;
import static com.badmitrii.mine.util.MineFieldParameters.*;
import java.util.function.BiConsumer;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.IntegerValidator;

import com.badmitrii.util.Parameters;
import com.google.inject.assistedinject.Assisted;

class MineFieldImpl implements MineField {

	private final MineFieldType[][] field;

	@Inject
	public MineFieldImpl(@Assisted Parameters parameters) {
		int rows = parameters.get(ROWS);
		int columns = parameters.get(COLUMNS);
		int bombs = parameters.get(BOMBS);
		if (rows <= 0)
			throw new IllegalArgumentException("rows must be postive: " + rows);
		if (columns <= 0)
			throw new IllegalArgumentException("columns must be postive: " + columns);
		Validate.exclusiveBetween(0, columns * rows, bombs,
				String.format("Bombs count must be a value from the interval (%s, %s). Bombs: %s", 0, rows * columns, bombs));
		field = new MineFieldType[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i * rows + j < bombs)
					field[i][j] = MineFieldType.BOMB;
				else
					field[i][j] = MineFieldType.EMPTY;
			}
		}
		shuffle();
	}

	public void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc) {
		boolean[][] visited = new boolean[field.length][];
		for (int i = 0; i < field.length; i++) {
			visited[i] = new boolean[field[i].length];
		}
		iterateEmptyFields(x, y, bc, visited);
	}

	@Override
	public final void shuffle() {
		Random rnd = new Random();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				int rndX = rnd.nextInt(i + 1), rndY = rnd.nextInt(j + 1);
				MineFieldType tmp = field[i][j];
				field[i][j] = field[rndX][rndY];
				field[rndX][rndY] = tmp;
			}
		}
	}

	private void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc, boolean[][] visited) {
		if (visited[x][y] == true)
			return;
		visited[x][y] = true;
		bc.accept(x, y);
	}

	@Override
	public int adjacentCount(int x, int y, MineFieldType mft) {
		int[] count = new int[1];
		processAdjacent(x, y, (i, j) -> {
			if (field[i][j] == mft)
				count[0]++;
		});
		return count[0];
	}

	private void processAdjacent(int x, int y, BiConsumer<Integer, Integer> bc) {
		int[] coordinates = { -1, 0, 1 };
		IntegerValidator v = IntegerValidator.getInstance();
		for (int i : coordinates) {
			for (int j : coordinates) {
				if ((i != 0 || j != 0) && v.isInRange(x + i, 0, field.length) && v.isInRange(y + j, 0, field[x + i].length))
					bc.accept(x + i, y + j);
			}
		}
	}

	public void iterate(BiConsumer<Integer, Integer> bc) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				bc.accept(i, j);
			}
		}
	}

	@Override
	public MineFieldType get(int x, int y) {
		return field[x][y];
	}
}
