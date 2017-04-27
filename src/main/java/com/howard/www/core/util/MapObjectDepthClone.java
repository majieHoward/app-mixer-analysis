package com.howard.www.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author howard
 * 
 */
public class MapObjectDepthClone {
	public static ConcurrentHashMap<String, Object> mapDeepCopy(
			ConcurrentHashMap<String, Object> mapOfPrimitive) throws Exception {
		ConcurrentHashMap<String, Object> mapOfDuplicate = new ConcurrentHashMap<String, Object>();
		int taskSize = 10;
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);
		CountDownLatch latch = new CountDownLatch(mapOfPrimitive.size());
		for (ConcurrentHashMap.Entry<String, Object> itemOfPrimitive : mapOfPrimitive
				.entrySet()) {
			StructureMapObjectElement mapObjectElement = new StructureMapObjectElement();
			mapObjectElement.initMapObjectElement(mapOfDuplicate,
					itemOfPrimitive, latch);
			pool.submit(mapObjectElement);
		}
		latch.await();
		pool.shutdown();
		return mapOfDuplicate;
	}
}
