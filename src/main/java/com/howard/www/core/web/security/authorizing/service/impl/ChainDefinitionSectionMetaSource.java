package com.howard.www.core.web.security.authorizing.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.security.authorizing.dao.IChainDefinitionSectionMetaSourceDao;

/**
 * 链定义部分元源
 * 
 * @author howard
 * 
 */
public class ChainDefinitionSectionMetaSource implements
		FactoryBean<ConcurrentHashMap<String, String>> {
	protected final Logger log = LoggerFactory
			.getLogger(ChainDefinitionSectionMetaSource.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	private IChainDefinitionSectionMetaSourceDao obtainIChainDefinitionSectionMetaSourceDao() {
		return (IChainDefinitionSectionMetaSourceDao) cApplicationContext
				.getBean("chainDefinitionSectionMetaSourceDao");
	}

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void obtainMenuInformation() throws Exception {
		List<JSONObject> itemsOfMenuInformation = obtainIChainDefinitionSectionMetaSourceDao()
				.obtainAllMenuItemResourcesFromDB(new DataTransferObject());
		for (JSONObject itemOfMenuInformation : itemsOfMenuInformation) {

		}
	}

	/**
	 * To grant authorization Abstract methods from the interface
	 * 
	 * Initialization execution method
	 */
	public ConcurrentHashMap<String, String> getObject() throws Exception {
		/**
		 * Structure menu permissions list
		 */
		ConcurrentHashMap<String, String> authorizationOfItems = new ConcurrentHashMap<String, String>();
		authorizationOfItems.put("/analysisadmin/analysis/login.html",
				"frameworkanon,frameworkLoginStatus");
		authorizationOfItems.put("/analysisadmin/analysis/doLogin.html",
				"frameworkanon");
		authorizationOfItems.put("/analysisadmin/analysis/updateChain.html",
				"frameworkanon");
		authorizationOfItems.put("/analysisadmin/analysis/logout.html",
				"frameworkauthc");
		authorizationOfItems.put("/assets/**", "frameworkanon");
		/**
		 * index
		 */
//		authorizationOfItems.put("/analysisadmin/analysis/index.html",
//				"frameworkauthc");
		/**
		 * stationEquipment
		 */
//		authorizationOfItems.put("/analysisadmin/analysis/stationEquipment/analysisStationRelationship.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/stationEquipment/stationEquipment.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/stationEquipment/stationEquipmentOfRealTimeMonitor.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/analysisRealtimeInformation/analysisRealtimeInformation.html",
//				"frameworkauthc");
		/**
		 * travelShow
		 */
//		authorizationOfItems.put("/analysisadmin/analysis/travelShow/travelShow.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/travelShow/travelCarSelectShow.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/travelShow/travelShowMovingView.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/travelShow/travelShowInfoItems.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/travelShow/travelShowExhibitionInfoItems.html",
//				"frameworkauthc");
		/**
		 * mobile
		 */
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/login.html",
//				"frameworkanon");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/index.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/stationEquipment/stationEquipment.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/dataStatistic/queryStatisticsLineCharts.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/dataStatistic/queryStatisticsBarCharts.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/stationEquipment/stationEquipmentItem.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/stationEquipment/stationEquipmentIndex.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/travelShow/travelShow.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/travelShow/travelShowIndex.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/travelShow/travelShowTripItem.html",
//				"frameworkauthc");
//		authorizationOfItems.put("/analysisadmin/analysis/mobile/travelShow/travelShowTripRunning.html",
//				"frameworkauthc");
		return authorizationOfItems;
	}

	/**
	 * To grant authorization Abstract methods from the interface
	 */
	public Class<?> getObjectType() {
		return this.getClass();
	}

	/**
	 * To grant authorization Abstract methods from the interface
	 */
	public boolean isSingleton() {
		return false;
	}

}
