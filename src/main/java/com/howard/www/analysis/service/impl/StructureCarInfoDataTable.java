package com.howard.www.analysis.service.impl;

import java.util.concurrent.CountDownLatch;

import net.sf.json.JSONObject;

import com.howard.www.analysis.entity.CarInfoDataTableEntity;
import com.howard.www.analysis.entity.CarInfoEntity;

public class StructureCarInfoDataTable extends Thread {
	/**
	 * latch
	 */
	private CountDownLatch latch;

	private CarInfoDataTableEntity carInfoDataTableEntity;

	private JSONObject carInfoItem;

	public StructureCarInfoDataTable() {

	}

	public StructureCarInfoDataTable(CountDownLatch latch,
			CarInfoDataTableEntity carInfoDataTableEntity,
			JSONObject carInfoItem) {
		this.latch = latch;
		this.carInfoDataTableEntity = carInfoDataTableEntity;
		this.carInfoItem = carInfoItem;
	}

	public void initStructureCarInfoDataTable(CountDownLatch latch,
			CarInfoDataTableEntity carInfoDataTableEntity,
			JSONObject carInfoItem) {
		this.latch = latch;
		this.carInfoDataTableEntity = carInfoDataTableEntity;
		this.carInfoItem = carInfoItem;
	}

	private void structureCarInfo() throws Exception {
		CarInfoEntity carInfoEntity = new CarInfoEntity();
		carInfoEntity.initCarInfo(this.carInfoItem);
		carInfoDataTableEntity.setAaData(carInfoEntity);
	};

	public void run() {
		try {
			structureCarInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

}
