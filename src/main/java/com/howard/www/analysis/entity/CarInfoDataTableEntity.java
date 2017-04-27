package com.howard.www.analysis.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("carInfoDataTableEntity")
@Scope("prototype")
public class CarInfoDataTableEntity extends BaseDataTableEntity<CarInfoEntity> {

}
