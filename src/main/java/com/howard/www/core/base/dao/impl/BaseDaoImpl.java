package com.howard.www.core.base.dao.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

public class BaseDaoImpl extends AbstractBaseDao {
	public JSONObject qryJqGridTable(IDataTransferObject param)
			throws Exception {
		int START_PAGE = 1;
		int PAGE_SIZE = -1;
		JSONObject returnObject = new JSONObject();
		/**
		 * paramMap保存请求参数
		 */
		String records = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("records"));
		/**
		 * sql
		 */
		String sqlName = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("paramSqlNameOfValue"));
		/**
		 * 
		 */
		String startPage = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("startPage"));
		if (!startPage.equals("")) {
			START_PAGE = Integer.valueOf(startPage);
		}
		String pageSize = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("pageSize"));
		if (!pageSize.equals("")) {
			PAGE_SIZE = Integer.valueOf(pageSize);
		}
		int count = obtainQuery().evaluateSqlResourceKey(sqlName)
				.evaluateIDataTransferObject(param).forCount();
		String sqlSentence = obtainQuery().evaluateSqlResourceKey(sqlName)
				.evaluateIDataTransferObject(param).obtainSqlSentence();
		StringBuffer pageStr = new StringBuffer();
		pageStr.append("select  " 
				+ " * from ( select row_limit.*, rownum rownum_ from (");
		pageStr.append(sqlSentence);
		pageStr.append(" ) row_limit where rownum <= ");
		pageStr.append(START_PAGE * PAGE_SIZE);
		pageStr.append(" ) where rownum_ >");
		pageStr.append((START_PAGE - 1) * PAGE_SIZE);
		JSONArray itemsOfData=obtainQuery().evaluateSqlResourceKey(sqlName)
				.evaluateIDataTransferObject(param).forJSONArrayPage(START_PAGE,PAGE_SIZE);
		returnObject.put("countOfValue", count);
		returnObject.put("sqlSentenceValue", pageStr.toString());
		returnObject.put("itemsOfDataValue", itemsOfData);
		returnObject.put("totalPagesValue",
				(int) Math.ceil((double) count / (double) PAGE_SIZE));
		returnObject.put("currPageValue", START_PAGE);
		return returnObject;
	}
	
	public JSONObject qryJqGridMobileTable(IDataTransferObject param)
			throws Exception {
		int START_PAGE = 1;
		int PAGE_SIZE = -1;
		JSONObject returnObject = new JSONObject();
		/**
		 * paramMap保存请求参数
		 */
		String records = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("records"));
		/**
		 * sql
		 */
		String sqlName = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("paramSqlNameOfValue"));
		/**
		 * 
		 */
		String startPage = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("page"));
		if (!startPage.equals("")) {
			START_PAGE = Integer.valueOf(startPage);
		}
		String pageSize = FrameworkStringUtil.asString(param
				.obtainRequestParamsMap().get("rows"));
		if (!pageSize.equals("")) {
			PAGE_SIZE = Integer.valueOf(pageSize);
		}
		int count = obtainQuery().evaluateSqlResourceKey(sqlName)
				.evaluateIDataTransferObject(param).forCount();
		String sqlSentence = obtainQuery().evaluateSqlResourceKey(sqlName)
				.evaluateIDataTransferObject(param).obtainSqlSentence();
		JSONArray itemsOfData=obtainQuery().evaluateSqlResourceKey(sqlName)
				.evaluateIDataTransferObject(param).forJSONArrayPage(START_PAGE,PAGE_SIZE);
		returnObject.put("countOfValue", count);
		returnObject.put("sqlSentenceValue", sqlSentence);
		returnObject.put("itemsOfDataValue", itemsOfData);
		returnObject.put("totalPagesValue",
				(int) Math.ceil((double) count / (double) PAGE_SIZE));
		returnObject.put("currPageValue", START_PAGE);
		return returnObject;
	}
}
