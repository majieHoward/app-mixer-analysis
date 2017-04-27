package com.howard.www.analysis.service.impl;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.springframework.stereotype.Repository;

import com.howard.www.analysis.entity.CarInfoEntity;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("comparatorCarInfoValue")
public class ComparatorCarInfoValue implements Comparator<CarInfoEntity> {

	public int compare(CarInfoEntity carInfoEntityOne,
			CarInfoEntity carInfoEntityTwo) {
		int flag = 0;
		try {
			Method methodOfIsEventIdentificationOne = carInfoEntityOne
					.getClass().getMethod("getCarNbr");
			Method methodOfIsEventIdentificationTwo = carInfoEntityTwo
					.getClass().getMethod("getCarNbr");

			flag = FrameworkStringUtil.asString(
					methodOfIsEventIdentificationOne.invoke(carInfoEntityOne,
							null)).compareTo(
					FrameworkStringUtil
							.asString(methodOfIsEventIdentificationTwo.invoke(
									carInfoEntityTwo, null)));
		} catch (Exception e) {

			e.printStackTrace();
		}
		return flag;
	}
}
