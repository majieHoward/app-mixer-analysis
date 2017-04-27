package com.github.abel533.echarts.util;

import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
/**
 * 
 * @author howard 2016-4-12
 *
 */
public class EnhancedOption extends GsonOption {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 输出到控制台
	 */
	public void print() {
		GsonUtil.print(this);
	}

	/**
	 * 输出到控制台
	 */
	public void printPretty() {
		GsonUtil.printPretty(this);
	}

	/**
	 * 在浏览器中查看
	 */
	public void view() {
		
	}

	/**
	 * 导出到指定文件名
	 * 
	 * @param fileName
	 * @return 返回html路径
	 */
	public String exportToHtml(String fileName) {
		return null;
	}
}
