package com.howard.www.core.web.security.authorizing.dao.impl;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;

import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.security.authorizing.dao.IChainDefinitionSectionMetaSourceDao;

/**
 * 
 * @author majie
 * 
 */
@Repository("chainDefinitionSectionMetaSourceDao")
public class ChainDefinitionSectionMetaSourceDaoImpl extends BaseDaoImpl
		implements IChainDefinitionSectionMetaSourceDao {

	public JSONArray obtainAllMenuItemResourcesFromDB(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery()
				.evaluateSqlResourceKey("namespaceAnalysis.sqlname6")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
