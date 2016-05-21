package com.badmitrii.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

class ParametersImpl implements Parameters{
	
	private final Map<Object, Object> storage;
	
	public ParametersImpl() {
		storage =  new HashMap<>();
	}
	
	public ParametersImpl(Supplier<Map<Object, Object>> storageSupplier){
		storage = storageSupplier.get();
	}

	public <T> void put(ParameterMetaData<T> metaData, T value) {
		storage.put(metaData, value);
	}

	public <T> T get(ParameterMetaData<T> metaData) {
		//safe as long as we modify the storage 
		//through the put method
		@SuppressWarnings("unchecked")
		T retVal = (T) storage.get(metaData);
		return retVal;
	}
}