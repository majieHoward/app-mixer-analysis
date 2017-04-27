package com.howard.www.analysis.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howard.www.analysis.service.IAnalysisRealTimeAcquisitionService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisRealTimeAcquisitionAction extends BaseController {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IAnalysisRealTimeAcquisitionService obtainAnalysisRealTimeAcquisitionService()
			throws Exception {
		
		return (IAnalysisRealTimeAcquisitionService) cApplicationContext
				.getBean("analysisRealTimeAcquisitionService");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/excavate/realTimeLocationInfoMonitor.html")
	@ResponseBody
	public void realTimeLocationInfoMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}

}
