package com.howard.www.analysis.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.howard.www.analysis.service.IAnalysisLoginPageResourcesService;
import com.howard.www.analysis.service.IAnalysisSystemMenuService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.vo.impl.FrameworkResult;
import com.howard.www.core.web.security.service.impl.FrameworkShiroFilerChainManager;

import net.sf.json.JSONObject;

/**
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisMenuAction extends BaseController {
	@Autowired
	private ApplicationContext cApplicationContext;

	@RequestMapping(value = "/menu.html")
	public String menu(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		IDataTransferObject paramOfDto = this.getParamOfDto();
		StringBuffer loginPageContext = new StringBuffer();
		try {

			IAnalysisLoginPageResourcesService analysisLoginPageResources = (IAnalysisLoginPageResourcesService) cApplicationContext
					.getBean("analysisLoginPageResourcesService");
			loginPageContext.append(analysisLoginPageResources.structureAnalysisLoginPage(paramOfDto));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.structurePageTemplate(model, "demo", loginPageContext.toString());
		return "login";
	}

	@RequestMapping(value = "/updateChain.html")
	@ResponseBody
	public void updateChain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FrameworkShiroFilerChainManager chainManager = (FrameworkShiroFilerChainManager) cApplicationContext
				.getBean("frameworkShiroFilerChainManager");
		chainManager.evaluateFilterChains(null);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		FrameworkResult result = null;
		result = new FrameworkResult(true, "更新权限列表");
		writer.write(JSONObject.fromObject(result).toString());
		writer.flush();
	}

	@RequestMapping(value = "/index.html")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMap(this.getParamOfDto(), model);
		return "index";
	}

	/**
	 * 用户范围未经授权的页面跳转到的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unauthorized.html")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMap(this.getParamOfDto(), model);
		return "unauthorized";
	}

	@RequestMapping(value = "/kickout.html")
	public String kickout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "kickout";
	}

}
