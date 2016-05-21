package com.badmitrii.util;

/**
 * Type-safe parameter container
 */
public interface Parameters {

	/**
	 * Associates {@code ParameterMetaData} with a given value. {@code null} value is permitted.
	 */
	public <T> void put(ParameterMetaData<T> metaData, T value);
	
	/**
	 * @return {@code null} - if there's no value associated with a given paremeter or if it's {@code null}.
	 */
	public <T> T get(ParameterMetaData<T> metaData);
}
