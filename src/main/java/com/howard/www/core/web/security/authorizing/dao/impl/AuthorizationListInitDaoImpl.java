package com.howard.www.core.web.security.authorizing.dao.impl;

import org.springframework.stereotype.Repository;

import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.security.authorizing.dao.IAuthorizationListInitDao;

import net.sf.json.JSONArray;

@Repository("authorizationListInitDao")
public class AuthorizationListInitDaoImpl extends BaseDaoImpl implements IAuthorizationListInitDao {

	public JSONArray obtainAllAuthorizationListFromDB(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.authorization")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
