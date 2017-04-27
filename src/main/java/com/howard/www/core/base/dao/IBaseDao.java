package com.howard.www.core.base.dao;

public interface IBaseDao {
	public IQuery obtainQuery();

	public IQuery obtainQuery(String queryBeanName);

	public IInsert obtainInsert();

	public IInsert obtainInsert(String insertBeanName);

	public ISequence obtainSequence();

	public ISequence obtainSequence(String sequenceBeanName);

	public IUpdate obtainUpdate();

	public IUpdate obtainUpdate(String updateBeanName);

	public IDelete obtainDelete();

	public IDelete obtainDelete(String deleteBeanName);
}
