package com.howard.www.analysis.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisTravelShowInfoDao;
import com.howard.www.analysis.entity.MarkPointEntity;
import com.howard.www.analysis.entity.RouteDisplayEntity;
import com.howard.www.analysis.entity.TravelShowInfoEntity;
import com.howard.www.analysis.entity.TravelShowInfoJqGridEntity;
import com.howard.www.analysis.service.IAnalysisTravelShowService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository("analysisTravelShowServiceImpl")
public class AnalysisTravelShowServiceImpl implements IAnalysisTravelShowService {
	@Autowired
	private ApplicationContext applicationContext;

	public JSONObject obtainTravelShowInfo(IDataTransferObject queryParameters) throws Exception {
		TravelShowInfoJqGridEntity tableEntity = new TravelShowInfoJqGridEntity();
		JSONObject itemsOfTravelShowInfo = obtainTravelShowServiceDao().obtainTravelShowServiceInfo(queryParameters);
		if (itemsOfTravelShowInfo != null) {
			if (itemsOfTravelShowInfo.get("itemsOfDataValue") != null
					&& ((JSONArray) itemsOfTravelShowInfo.get("itemsOfDataValue")).isArray()
					&& ((JSONArray) itemsOfTravelShowInfo.get("itemsOfDataValue")).size() > 0) {
				structureTravelShowInfoItems(tableEntity, (JSONArray) itemsOfTravelShowInfo.get("itemsOfDataValue"));
			}

		}
		tableEntity.initJqGridTableEntity(itemsOfTravelShowInfo);
		return JSONObject.fromObject(tableEntity);
	}

	private void structureTravelShowInfoItems(TravelShowInfoJqGridEntity tableEntity, JSONArray itemsOfTravelShowInfo)
			throws Exception {
		if (itemsOfTravelShowInfo != null && itemsOfTravelShowInfo.isArray()) {
			for (int i = 0; i < itemsOfTravelShowInfo.size(); i++) {
				tableEntity.setGridData(structureTravelShowInfoEntity(itemsOfTravelShowInfo.getJSONObject(i)));
			}
		}
	}

	private IAnalysisTravelShowInfoDao obtainTravelShowServiceDao() {
		return (IAnalysisTravelShowInfoDao) applicationContext.getBean("analysisTravelShowInfoDaoImpl");
	}

	public JSONObject obtainCurrentOperIdInformation(IDataTransferObject queryParameters) throws Exception {
		JSONObject itemsOfTravelShowInfo = obtainTravelShowServiceDao().obtaincurrentOperIdInformation(queryParameters);
		return JSONObject.fromObject(structureTravelShowInfoEntity(itemsOfTravelShowInfo));
	}

	private TravelShowInfoEntity structureTravelShowInfoEntity(JSONObject itemsOfTravelShowInfo) throws Exception {
		TravelShowInfoEntity travelShowInfoEntity = null;
		if (itemsOfTravelShowInfo != null) {
			travelShowInfoEntity = new TravelShowInfoEntity();
			travelShowInfoEntity.initTravelShowInfoEntity(itemsOfTravelShowInfo);
		}
		return travelShowInfoEntity;
	}

	/**
	 * 获得下一个operId对应的详细信息
	 */
	public JSONObject obtainNextOperIdInformation(IDataTransferObject queryParameters) throws Exception {
		JSONObject itemsOfTravelShowInfo = obtainTravelShowServiceDao().obtainNextOperIdInformation(queryParameters);
		return JSONObject.fromObject(structureTravelShowInfoEntity(itemsOfTravelShowInfo));
	}

	/**
	 * 获得相邻的两个operId之间的行程列表信息
	 */
	public JSONObject obtainTravelBetweenAdjacentNodes(IDataTransferObject queryParameters) throws Exception {
		RouteDisplayEntity routeDisplayEntity = new RouteDisplayEntity();
		JSONArray markPoints = obtainTravelShowServiceDao().obtainTravelBetweenAdjacentNodes(queryParameters);
		structureMarkPoints(routeDisplayEntity, markPoints);
		return JSONObject.fromObject(routeDisplayEntity);
	}

	private void structureMarkPoints(RouteDisplayEntity routeDisplayEntity, JSONArray markPoints) throws Exception {
		if (markPoints != null && markPoints.isArray()) {
			JSONObject markPoint;
			MarkPointEntity markPointEntity = null;
			for (int i = 0; i < markPoints.size(); i++) {
				markPoint = markPoints.getJSONObject(i);
				if (markPoint != null) {
					markPointEntity = new MarkPointEntity();
					markPointEntity.initMarkPointEntity(markPoint);
					routeDisplayEntity.setMarkPoints(markPointEntity);
				}
			}

		}
	}

	/**
	 * 获得两个相邻的operId之间的信息整合
	 */
	public JSONObject betweenTheTwoOperIdInformation(IDataTransferObject queryParameters) throws Exception {
		/**
		 * 获得下一个operId对应的详细信息
		 */
		JSONObject nextOperIdInfo = obtainNextOperIdInformation(queryParameters);
		/**
		 * 获得相邻的两个operId之间的行程列表信息
		 */
		JSONObject adjacentNodes = obtainTravelBetweenAdjacentNodes(queryParameters);

		ConcurrentHashMap<String, JSONObject> showInfomation = new ConcurrentHashMap<String, JSONObject>();
		showInfomation.put("nextOperIdInfo", nextOperIdInfo);
		showInfomation.put("adjacentNodes", adjacentNodes);
		System.out.println(JSONObject.fromObject(showInfomation));
		return JSONObject.fromObject(showInfomation);
	}

	public byte[] obtainTravelShowInfoExcelFile(IDataTransferObject queryParameters) throws Exception {
		ByteArrayOutputStream out = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            generateExcelForAs(obtainTravelShowServiceDao().obtainExcelDataItems(queryParameters), workbook);
            out = new ByteArrayOutputStream();
            HSSFWorkbook hssWb = (HSSFWorkbook) workbook;
            hssWb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return out.toByteArray();
	}
	private void generateExcelForAs(List<JSONObject> list, HSSFWorkbook workbook) {
        if (list == null || list.size() == 0) {
            return ;
        }
        JSONObject title = new JSONObject();
        title.put("1", "操作流水号");
        title.put("2", "设备编号");
        title.put("3", "经度");
        title.put("4", "维度");
        title.put("5", "重量");
        title.put("6", "时间");
        title.put("7", "重量");
        title.put("8", "时间");
        title.put("9", "重量差");
        title.put("10", "状态");
        title.put("11", "上下货状态");
        title.put("12", "车辆编号");
        title.put("13", "序列号");
        int excelRow = 0;
        try {
            HSSFSheet sheet = workbook.createSheet();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    JSONObject first = list.get(0);
                    // 添加标题
                    int column = 0;
                    HSSFRow row = sheet.createRow(excelRow);
                    Iterator it = title.keys();  
                    while(it.hasNext()){  
                        row.createCell(column).setCellValue(FrameworkStringUtil.asString(title.get(it.next().toString())));
                    	column++;
                    }  
                    excelRow++;
                    column = 0;
                    // 还需添加第1行的数据, 从0开始
                    row = sheet.createRow(excelRow);
                    it=first.keys();
                    while(it.hasNext()){ 
                        row.createCell(column).setCellValue(FrameworkStringUtil.asString(first.get(it.next().toString())));
                        column++;
                    }
                    excelRow++;
                } else {
                    HSSFRow row = sheet.createRow(excelRow);
                    JSONObject rowMap = list.get(i);
                    Iterator it = rowMap.keys(); 
                    int column = 0;
                    while(it.hasNext()){ 
                    	  row.createCell(column).setCellValue(FrameworkStringUtil.asString(rowMap.get(it.next().toString())));
                          column++;
                    }
                    excelRow++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
