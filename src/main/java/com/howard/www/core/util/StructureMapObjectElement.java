package com.howard.www.core.util;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class StructureMapObjectElement extends Thread {
	private ConcurrentHashMap<String, Object> mapOfDuplicate;
	private ConcurrentHashMap.Entry<String, Object> itemOfPrimitive;
	private CountDownLatch latch;

	public StructureMapObjectElement() {

	}

	public StructureMapObjectElement(
			ConcurrentHashMap<String, Object> mapOfDuplicate,
			Entry<String, Object> itemOfPrimitive, CountDownLatch latch) {
		super();
		this.initMapObjectElement(mapOfDuplicate, itemOfPrimitive, latch);
	}

	public void initMapObjectElement(
			ConcurrentHashMap<String, Object> mapOfDuplicate,
			Entry<String, Object> itemOfPrimitive, CountDownLatch latch) {
		this.mapOfDuplicate = mapOfDuplicate;
		this.itemOfPrimitive = itemOfPrimitive;
		this.latch = latch;
	}

	private void putOneOfElement() throws Exception {
		mapOfDuplicate
				.put(itemOfPrimitive.getKey(), itemOfPrimitive.getValue());
	}

	public void run() {
		try {
			putOneOfElement();
		} catch (Exception e) {
		}
		latch.countDown();
	}

}
