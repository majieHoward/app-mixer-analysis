package com.howard.www.analysis.dao.impl;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisPageMenuResourcesDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

/**
 * 
 * @author howard
 * 
 */
@Repository("analysisPageMenuResourcesDao")
public class AnalysisPageMenuResourcesDaoImpl extends BaseDaoImpl implements
		IAnalysisPageMenuResourcesDao {
	

	public JSONArray obtainAllMenuItemResourcesFromDB(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery()
				.evaluateSqlResourceKey("namespaceAnalysis.sqlname6")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
