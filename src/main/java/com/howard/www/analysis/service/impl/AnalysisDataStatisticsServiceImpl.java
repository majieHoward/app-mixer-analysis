package com.howard.www.analysis.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisDataStatisticsDao;
import com.howard.www.analysis.service.IAnalysisDataStatisticsService;
import com.howard.www.analysis.service.IAnalysisStructuralBrokenLineGraph;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository("analysisDataStatisticsServiceImpl")
public class AnalysisDataStatisticsServiceImpl implements IAnalysisDataStatisticsService {
	@Autowired
	private ApplicationContext applicationContext;

	private IAnalysisStructuralBrokenLineGraph obtainIAnalysisStructuralBrokenLineGraph(String beanName)
			throws Exception {
		return (IAnalysisStructuralBrokenLineGraph) applicationContext.getBean(beanName);
	}

	private IAnalysisDataStatisticsDao obtainIAnalysisDataStatisticsDao(String beanName) throws Exception {
		return (IAnalysisDataStatisticsDao) applicationContext.getBean(beanName);
	}

	private void initQueryData(IDataTransferObject queryParameters)throws Exception{
		String queryDate = FrameworkStringUtil
				.asString(queryParameters.obtainMapOfRequiredParameter().get("queryDate"));
		if ("".equals(queryDate)) {
			Date newDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String dateNowStr = sdf.format(newDate);
			queryParameters.evaluteRequiredParameter("queryDate", dateNowStr);
		}
	}
	public JSONObject obtainMonthlyTrafficVolumeByCarId(IDataTransferObject queryParameters) throws Exception {
		initQueryData(queryParameters);
		JSONArray valueOfitems = obtainIAnalysisDataStatisticsDao("analysisDataStatisticsDaoImpl")
				.obtainMonthlyTrafficVolumeByCarId(queryParameters);
		JSONObject paramObject = structureOriginalData(valueOfitems);
		return obtainIAnalysisStructuralBrokenLineGraph("analysisStructuralBrokenLineGraphLine6")
				.obtainDataStatisticsInfoItems(paramObject);
	}

	public JSONObject structureOriginalData(List<JSONObject> valueOfitems) throws Exception {
		JSONObject paramObject = new JSONObject();
		/**
		 * 构造标题和副标题
		 */
		paramObject.put("titleText", "");
		paramObject.put("subTitleText", "");
		/**
		 * 设置y轴显示的内容更
		 */
		String[] yaxisTextValueItems = new String[] { "运输量" };
		List<JSONObject> yaxisTextItems = new ArrayList<JSONObject>();
		JSONObject yaxisTextItem;
		for (String yaxisTextValueItem : yaxisTextValueItems) {
			yaxisTextItem = new JSONObject();
			yaxisTextItem.put("yaxisText", yaxisTextValueItem);
			yaxisTextItems.add(yaxisTextItem);
		}
		paramObject.put("yaxisTextItem", yaxisTextItems);
		List<JSONArray> xaxisTextItems = new ArrayList<JSONArray>();
		JSONArray categoryAxisItems = new JSONArray();
		JSONArray xaxisTextItem = new JSONArray();
		if(valueOfitems!=null&&valueOfitems.size()>0){
			for (JSONObject valueOfitem : valueOfitems) {
				categoryAxisItems.add(FrameworkStringUtil.asString(valueOfitem.get("RQ")));
				xaxisTextItem.add(FrameworkStringUtil.asString(valueOfitem.get("RQZLHJ")));
			}
		}
		
		xaxisTextItems.add(xaxisTextItem);
		paramObject.put("xaxisTextItems", xaxisTextItems);
		paramObject.put("categoryAxisItems", categoryAxisItems);
		return paramObject;
	}

	public JSONObject obtainMonthlyTrafficVolumeByBssOrg(IDataTransferObject queryParameters) throws Exception {
		initQueryData(queryParameters);
		JSONArray valueOfitems = obtainIAnalysisDataStatisticsDao("analysisDataStatisticsDaoImpl")
				.obtainMonthlyTrafficVolumeByBssOrg(queryParameters);
		JSONObject paramObject = structureOriginalData(valueOfitems);
		return obtainIAnalysisStructuralBrokenLineGraph("analysisStructuralBrokenLineGraphLine6")
				.obtainDataStatisticsInfoItems(paramObject);
	}

}
