package com.badmitrii.util;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Type-safe parameter container
 */
public interface Parameters {
	
	public static Parameters empty(){
		return new ParametersImpl();
	}
	
	/**
	 * Returns an empty {@code Parameters} with a given underlying storage.
	 * The supplier has to return an <b>empty</b> {@code Map}. 
	 */
	public static Parameters empty(Supplier<Map<Object, Object>> storageSupplier){
		return new ParametersImpl(storageSupplier);
	}

	/**
	 * Associates {@code ParameterMetaData} with a given value. {@code null} value is permitted.
	 */
	public <T> void put(ParameterMetaData<T> metaData, T value);
	
	/**
	 * @return {@code null} - if there's no value associated with a given parameter or if it's {@code null}.
	 */
	public <T> T get(ParameterMetaData<T> metaData);
}
