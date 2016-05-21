package com.badmitrii.mine.util;

import static com.badmitrii.mine.util.MineFieldParameters.*;
import com.badmitrii.util.Parameters;

public final class GameUtils {
	
	private GameUtils(){
		throw new Error("Not instantiatable");
	}
	
	public static Parameters expertParameters() {
		Parameters retVal = Parameters.empty();
		retVal.put(ROWS, 30);
		retVal.put(COLUMNS, 30);
		retVal.put(BOMBS, 100);
		return retVal;
	}

	public static Parameters mediumParameters() {
		Parameters retVal = Parameters.empty();
		retVal.put(ROWS, 20);
		retVal.put(COLUMNS, 20);
		retVal.put(BOMBS, 75);
		return retVal;
	}

	public static Parameters easyParameters() {
		Parameters retVal = Parameters.empty();
		retVal.put(ROWS, 10);
		retVal.put(COLUMNS, 10);
		retVal.put(BOMBS, 50);
		return retVal;
	}


}
