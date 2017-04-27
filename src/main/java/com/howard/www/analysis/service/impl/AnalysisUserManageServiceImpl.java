package com.howard.www.analysis.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.howard.www.analysis.dao.IAnalysisUserManageDao;
import com.howard.www.analysis.service.IAnalysisUserManageService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.vo.impl.FrameworkResult;
import com.howard.www.core.web.security.authorizing.saltencryption.IFrameworkSaltEncryption;
import com.howard.www.core.web.security.authorizing.service.IValidationLoginDataValidity;
import com.howard.www.core.web.util.FrameworkStringUtil;

@Repository("analysisUserManageService")
public class AnalysisUserManageServiceImpl implements
		IAnalysisUserManageService {
	@Autowired
	private ApplicationContext cApplicationContext;

	private IFrameworkSaltEncryption obtainIFrameworkSaltEncryption() {
		return (IFrameworkSaltEncryption) cApplicationContext
				.getBean("frameworkBaseSaltEncryption");
	}

	private IValidationLoginDataValidity obtainIValidationLoginDataValidity() {
		return (IValidationLoginDataValidity) cApplicationContext
				.getBean("analysisValidationLoginDataValidityService");
	}

	private IAnalysisUserManageDao obtainIAnalysisUserManageDao() {
		return (IAnalysisUserManageDao) cApplicationContext
				.getBean("analysisUserManageDao");
	}

	@Override
	public JSONObject verifyUserLogin(IDataTransferObject queryParameters)
			throws Exception {
		String validity = obtainIValidationLoginDataValidity()
				.validationDataValidity(queryParameters);
		FrameworkResult frameworkResult = (FrameworkResult) cApplicationContext
				.getBean("frameworkResult");
		if (!"".equals(FrameworkStringUtil.asString(validity))) {
			frameworkResult.setSuccess(false);
			frameworkResult.setMsg(validity);
		} else {
			JSONObject requestTheUserToLogIn = verifyUserPresence(queryParameters);
			if (requestTheUserToLogIn != null) {
				if (false == verifyTheUserIsLocked(requestTheUserToLogIn)) {
					frameworkResult.setSuccess(false);
					frameworkResult.setMsg("用户被锁定");
				} else {
					/**
					 * Execution framework login
					 */
					UsernamePasswordToken token = new UsernamePasswordToken(
							FrameworkStringUtil.asString(requestTheUserToLogIn
									.get("STAFF_NAME")),
							FrameworkStringUtil.asString(requestTheUserToLogIn
									.get("STAFF_PWD")));
					Subject subject = SecurityUtils.getSubject();
					subject.login(token);
					Session session = subject.getSession();
					/**
					 * 将用户的staffId保存的session中
					 */
					session.setAttribute("loginStaffId", FrameworkStringUtil.asString(requestTheUserToLogIn
							.get("STAFF_ID")));
					/**
					 * 构造保存在session中的数据结果集
					 */
					frameworkResult.setSuccess(true);
				}
			} else {
				frameworkResult.setSuccess(false);
				frameworkResult.setMsg("用户不存在");
			}
		}
		return JSONObject.fromObject(frameworkResult);
	}

	/**
	 * 
	 * @param queryParameters
	 * @return
	 * @throws Exception
	 */
	private JSONObject verifyUserPresence(IDataTransferObject queryParameters)
			throws Exception {
		/**
		 * 
		 */
		String loginUserPassword = FrameworkStringUtil.asString(queryParameters
				.obtainMapOfRequiredParameter().get("passWord"));
		String loginUserPasswordBySalt = FrameworkStringUtil
				.asString(obtainIFrameworkSaltEncryption()
						.encryptionThroughAlgorithm(loginUserPassword));
		if (!"".equals(loginUserPasswordBySalt)) {
			queryParameters.obtainMapOfRequiredParameter().remove("passWord");
			queryParameters.obtainMapOfRequiredParameter().put("passWord",
					loginUserPasswordBySalt);
			return obtainIAnalysisUserManageDao().obtainTheCurrentUserExists(
					queryParameters);
		}

		return null;
	}

	/**
	 * 
	 * @param requestTheUserToLogIn
	 * @return
	 * @throws Exception
	 */
	private boolean verifyTheUserIsLocked(JSONObject requestTheUserToLogIn)
			throws Exception {
		/**
		 * To determine whether the user is locked
		 */
		String stateOfUser = FrameworkStringUtil.asString("state");
		if ("10A".equals(stateOfUser)) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public JSONArray obtainTheCurrentUserRoleItemsOfStation(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray obtainTheCurrentUserPermissionOfStation(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject executeTheCurrentUserExit(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray obtainTheCurrentUserStationItem(
			IDataTransferObject queryParameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
