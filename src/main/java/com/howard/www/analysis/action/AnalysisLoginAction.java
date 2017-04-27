package com.howard.www.analysis.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.howard.www.analysis.service.IAnalysisLoginPageResourcesService;
import com.howard.www.analysis.service.IAnalysisSystemMenuService;
import com.howard.www.analysis.service.IAnalysisUserManageService;
import com.howard.www.core.base.web.controller.BaseController;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;

/**
 * 
 * @author howard
 * 
 */
@Controller
@Scope("prototype")
public class AnalysisLoginAction extends BaseController {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IAnalysisUserManageService obtainAnalysisUserManageService() throws Exception {
		return (IAnalysisUserManageService) cApplicationContext.getBean("analysisUserManageService");
	}

	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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

	@RequestMapping(value = "/doLogin.html")
	@ResponseBody
	public void doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDataTransferObject paramOfDto = this.getParamOfDto();
		JSONObject userLoginFeedback = obtainAnalysisUserManageService().verifyUserLogin(paramOfDto);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(userLoginFeedback.toString());
		writer.flush();
	}

	@RequestMapping(value = "/logout.html")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			// session 会销毁，在SessionListener监听session销毁，清理权限缓存
			subject.logout();
		}
		/**
		 * 
		 */
		return "redirect:login.html";
	}

	@RequestMapping(value = "/notFound")
	public String selfNotFound(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		IAnalysisSystemMenuService menuService = (IAnalysisSystemMenuService) cApplicationContext
				.getBean("analysisSystemMenuService");
		menuService.obtainSystemMenuItemsModelMap(this.getParamOfDto(), model);
		return "notFound";
	}
}
