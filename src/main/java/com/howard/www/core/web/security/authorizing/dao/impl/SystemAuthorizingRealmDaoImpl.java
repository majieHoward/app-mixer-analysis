package com.howard.www.core.web.security.authorizing.dao.impl;

import org.springframework.stereotype.Repository;

import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.security.authorizing.dao.ISystemAuthorizingRealmDao;

import net.sf.json.JSONArray;
@Repository("systemAuthorizingRealmDao")
public class SystemAuthorizingRealmDaoImpl extends BaseDaoImpl implements ISystemAuthorizingRealmDao {

	public JSONArray obtainAllPermissionsFromDB(IDataTransferObject queryParameters) throws Exception {
		return obtainQuery().evaluateSqlResourceKey("namespaceAnalysis.permissions")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
