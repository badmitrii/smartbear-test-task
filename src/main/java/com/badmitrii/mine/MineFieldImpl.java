package com.badmitrii.mine;

import static com.badmitrii.mine.util.MineFieldParameters.BOMBS;
import static com.badmitrii.mine.util.MineFieldParameters.COLUMNS;
import static com.badmitrii.mine.util.MineFieldParameters.ROWS;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.IntegerValidator;

import com.badmitrii.util.Parameters;
import com.google.inject.assistedinject.Assisted;

final class MineFieldImpl implements MineField {
	
	private final boolean visited[][];
	private final MineFieldType[][] field;

	/**
	 * Puts all mines in the begin.
	 */
	@Inject
	MineFieldImpl(@Assisted Parameters parameters) {
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
		visited = new boolean[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i * rows + j < bombs)
					field[i][j] = MineFieldType.BOMB;
				else
					field[i][j] = MineFieldType.EMPTY;
			}
		}
	}
	
	@Override
	public void iterate(BiConsumer<Integer, Integer> bc) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				bc.accept(i, j);
			}
		}
	}

	@Override
	public final void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc) {
		validate(x, y);
		if(get(x, y) == MineFieldType.BOMB)
			return;
		iterateEmptyFields(x, y, bc, visited);
	}

	@Override
	public MineFieldImpl shuffle() {
		Random rnd = new Random();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				int rndX = rnd.nextInt(i + 1), rndY = rnd.nextInt(j + 1);
				MineFieldType tmp = field[i][j];
				field[i][j] = field[rndX][rndY];
				field[rndX][rndY] = tmp;
			}
		}
		return this;
	}

	private void iterateEmptyFields(int x, int y, BiConsumer<Integer, Integer> bc, boolean[][] visited) {
		Set<Point> pointsToProcess = new HashSet<>();
		pointsToProcess.add(new Point(x, y));
		do {
			pointsToProcess.forEach(p -> {
				if(!visited[p.x][p.y]){
					bc.accept(p.x, p.y);
					visited[p.x][p.y] = true;
				}
			});

			Set<Point> nextToProcess = new HashSet<>();
			BiConsumer<Integer, Integer> putIfNotVisited = (i, j) -> {
				if(!visited[i][j])
					nextToProcess.add(new Point(i, j));
			};
			pointsToProcess.stream()
							.filter(p -> adjacentCount(p.x, p.y, MineFieldType.BOMB) == 0)
							.forEach(p -> processAdjacent(p.x, p.y, putIfNotVisited));
			pointsToProcess = nextToProcess;
		} while(!pointsToProcess.isEmpty());
	}

	@Override
	public int adjacentCount(int x, int y, MineFieldType mft) {
		validate(x, y);
		int[] count = new int[1];
		processAdjacent(x, y, (i, j) -> {
			if (field[i][j] == mft)
				count[0]++;
		});
		return count[0];
	}
	

	private void processAdjacent(int x, int y, BiConsumer<Integer, Integer> bc) {
		IntegerValidator v = IntegerValidator.getInstance();
		IntStream.range(-1, 2)
				.forEach(i -> {
			IntStream.range(-1, 2)
					.filter(j -> (i != 0 || j != 0) 
									&& (v.isInRange(x + i, 0, field.length - 1) 
									&& (v.isInRange(y + j, 0, field[x + i].length - 1))))
					.forEach(j -> bc.accept(x + i, y + j));
		});
	}

	@Override
	public final MineFieldType get(int x, int y) {
		return field[x][y];
	}
	
	private void validate(int x, int y) {
		if(x < 0 || y < 0)
			throw new IndexOutOfBoundsException("Cannot proceed with negative coordinates");
		if(x >= field.length || y >= field[x].length)
			throw new IndexOutOfBoundsException("x, y:" + x + ", " + y);
	}
	
	private static class Point{
		final int x, y;
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object o){
			if(!(o instanceof Point))
				return false;
			Point p = (Point) o;
			return p.x == x && p.y == y;
		}
		
		@Override
		public int hashCode(){
			int retVal = 17;
			retVal = x * 31 + retVal;
			retVal = y * 31 + retVal;
			return retVal;
		}
	}
}
