package com.howard.www.core.base.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.howard.www.core.base.dao.IBaseDao;
import com.howard.www.core.base.dao.IDelete;
import com.howard.www.core.base.dao.IInsert;
import com.howard.www.core.base.dao.IQuery;
import com.howard.www.core.base.dao.ISequence;
import com.howard.www.core.base.dao.IUpdate;

/**
 * 
 * @author howard
 * 
 */
public abstract class AbstractBaseDao implements IBaseDao {
	@Autowired
	private ApplicationContext cApplicationContext;

	public IQuery obtainQuery() {
		return obtainQuery("baseQuery");
	}

	public IInsert obtainInsert() {
		return obtainInsert("baseInsert");
	}

	public IQuery obtainQuery(String queryBeanName) {

		return (IQuery) cApplicationContext.getBean(queryBeanName);
	}

	public IInsert obtainInsert(String insertBeanName) {

		return (IInsert) cApplicationContext.getBean(insertBeanName);
	}

	public ISequence obtainSequence() {
		return obtainSequence("baseSequence");
	}

	public ISequence obtainSequence(String sequenceBeanName) {
		return (ISequence) cApplicationContext.getBean(sequenceBeanName);
	};

	public IUpdate obtainUpdate() {
		return obtainUpdate("baseUpdate");
	};

	public IUpdate obtainUpdate(String updateBeanName) {
		return (IUpdate) cApplicationContext.getBean(updateBeanName);
	};

	public IDelete obtainDelete() {
		return obtainDelete("baseDelete");
	};

	public IDelete obtainDelete(String deleteBeanName) {
		return (IDelete) cApplicationContext.getBean(deleteBeanName);
	};

}
