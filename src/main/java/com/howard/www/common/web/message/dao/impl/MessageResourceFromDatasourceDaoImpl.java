package com.howard.www.common.web.message.dao.impl;

import org.springframework.stereotype.Repository;
import net.sf.json.JSONArray;
import com.howard.www.common.web.message.dao.IMessageResourceFromDatasourceDao;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

/**
 * 
 * @author howard
 * 
 */
@Repository("messageResourceFromDatasourceDao")
public class MessageResourceFromDatasourceDaoImpl extends BaseDaoImpl implements
		IMessageResourceFromDatasourceDao {

	public JSONArray obtainMessageResourceResultSet(IDataTransferObject queryParameters)
			throws Exception {
 
		return obtainQuery().evaluetePrimitiveSqlResource("select * from ${messageResourceTableName} <if test='elementName != null ' >where pageElementName = '${elementName}' </if>")
				.evaluateIDataTransferObject(queryParameters).forJsonArray();
	}

}
