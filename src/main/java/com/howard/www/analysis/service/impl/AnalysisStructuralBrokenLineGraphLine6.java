package com.howard.www.analysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.util.EnhancedOption;
import com.howard.www.analysis.service.IAnalysisStructuralBrokenLineGraph;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository("analysisStructuralBrokenLineGraphLine6")
public class AnalysisStructuralBrokenLineGraphLine6 implements IAnalysisStructuralBrokenLineGraph {

	public JSONObject obtainDataStatisticsInfoItems(JSONObject paramObject) throws Exception {
		// 地址:http://echarts.baidu.com/doc/example/line6.html
		EnhancedOption option = new EnhancedOption();
		tectoniTitle(paramObject, option);
//		option.tooltip().trigger(Trigger.axis).formatter("function(v){" + "return v[0][1] + '<br>'"
//				+ " + v[0][0] + ' : ' + v[0][2] + ' (m^3/s)<br/>'" + "+ v[1][0] + ' : ' + -v[1][2] + ' (mm)';" + "}");
		option.toolbox().show(false).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
				Tool.restore, Tool.saveAsImage);
		JSONArray categoryAxisItems = paramObject.getJSONArray("categoryAxisItems");
		option.dataZoomNew().show(true).realtime(true).start(0).end(categoryAxisItems.size()-1);
		tectoniYaxisText(paramObject, option);
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.boundaryGap(false).axisLine().onZero(false);
		categoryAxis.setData(categoryAxisItems);
		option.xAxis(categoryAxis);
		tectoniXaxisValue(paramObject, option);
		JSONObject optionData=JSONObject.fromObject(GsonUtil.format(option));
		JSONArray dataZoomItems=optionData.getJSONArray("dataZoom");
		optionData.put("dataZoom", dataZoomItems.get(0));
		return optionData;
	}

	/**
	 * 设置主标题负标题
	 * 
	 * @param paramObject
	 * @param option
	 * @throws Exception
	 */
	public void tectoniTitle(JSONObject paramObject, EnhancedOption option) throws Exception {
		String titleText = FrameworkStringUtil.asString(paramObject.get("titleText"));
		String subTitleText = FrameworkStringUtil.asString(paramObject.get("subTitleText"));
		option.title().text(titleText).subtext(subTitleText).x(X.center);
	}

	/**
	 * 设置y轴显示的中文
	 * 
	 * @param paramObject
	 * @param option
	 * @throws Exception
	 */
	public void tectoniYaxisText(JSONObject paramObject, EnhancedOption option) throws Exception {
		List<JSONObject> yaxisTextItems = paramObject.getJSONArray("yaxisTextItem");
		List<String> legendTextItems = new ArrayList<String>();
		if (yaxisTextItems != null && yaxisTextItems.size() > 0) {
			for (JSONObject yaxisTextItem : yaxisTextItems) {
				legendTextItems.add(FrameworkStringUtil.asString(yaxisTextItem.get("yaxisText")));
				option.yAxis(new ValueAxis().name(FrameworkStringUtil.asString(yaxisTextItem.get("yaxisText"))));
			}
		}
		Legend legend = new Legend();
		legend.setData(legendTextItems);
		option.setLegend(legend);
		option.legend().x(X.left);
	}

	public void tectoniXaxisValue(JSONObject paramObject, EnhancedOption option) throws Exception {
		List<JSONObject> yaxisTextItems = paramObject.getJSONArray("yaxisTextItem");
		List<JSONArray> xaxisTextItems = paramObject.getJSONArray("xaxisTextItems");
		List<Series> series = new ArrayList<Series>();
		if (yaxisTextItems != null && yaxisTextItems.size() > 0 && xaxisTextItems != null
				&& xaxisTextItems.size() > 0) {
			Line line;
			for (int i = 0; i < yaxisTextItems.size(); i++) {
				line = new Line(FrameworkStringUtil.asString(yaxisTextItems.get(i).get("yaxisText")));
				line.setData(xaxisTextItems.get(i));
				line.yAxisIndex(i).itemStyle().normal().areaStyle().typeDefault();
				series.add(line);
			}
		}
		option.setSeries(series);
	}
}
