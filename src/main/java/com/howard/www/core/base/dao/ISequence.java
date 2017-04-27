package com.howard.www.core.base.dao;

public interface ISequence extends IHandleSql<ISequence> {
	public Long obtainValueOfSequence();
}
