package com.howard.www.core.web.startup.certificate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import net.sf.json.JSONArray;
import com.howard.www.core.base.dao.impl.BaseDaoImpl;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.startup.certificate.dao.IGainPropertyFromDatasourceConfigDao;

@Repository("gainPropertyFromDatasourceConfigDao")
public class GainPropertyFromDatasourceConfigDaoImpl extends BaseDaoImpl
		implements IGainPropertyFromDatasourceConfigDao {
	protected final Logger log = LoggerFactory
			.getLogger(GainPropertyFromDatasourceConfigDaoImpl.class);

	public JSONArray obtainAllTheAppInitparameters(
			IDataTransferObject queryParameters) throws Exception {
		return obtainQuery()
				.evaluateSqlResource("select * from INITPARAMETERS")
				.forJsonArray();
	}

}
