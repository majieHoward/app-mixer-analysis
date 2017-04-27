package com.howard.www.analysis.service.impl;

import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import com.howard.www.analysis.dao.IAnalysisPageMenuResourcesDao;
import com.howard.www.analysis.service.IAnalysisPageMenuResourcesService;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;

/**
 * 
 * @author howard
 * 
 */
@Repository("analysisPageMenuResourcesService")
public class AnalysisPageMenuResourcesServiceImpl implements
		IAnalysisPageMenuResourcesService, InitializingBean, DisposableBean {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IAnalysisPageMenuResourcesDao obtainIAnalysisPageMenuResourcesDao(
			String analysisPageMenuResourcesDaoBeanName) {
		return (IAnalysisPageMenuResourcesDao) cApplicationContext
				.getBean(analysisPageMenuResourcesDaoBeanName);
	}

	public void destroy() throws Exception {

	}

	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		List<JSONObject> menuEntityItems = obtainIAnalysisPageMenuResourcesDao(
				"analysisPageMenuResourcesDao")
				.obtainAllMenuItemResourcesFromDB(new DataTransferObject());
		if(menuEntityItems!=null){
			for (JSONObject menuEntityItem : menuEntityItems) {
				
			}
		}
	}

}
