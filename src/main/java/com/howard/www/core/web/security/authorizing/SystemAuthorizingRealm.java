package com.howard.www.core.web.security.authorizing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.data.transfer.dto.impl.DataTransferObject;
import com.howard.www.core.web.security.authorizing.dao.ISystemAuthorizingRealmDao;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

/**
 * 用户的授权信息不将授权信息存储到cache中每次都重新从数据库中查询
 * 
 * @author howard
 * 
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {
	protected final Logger log = LoggerFactory.getLogger(SystemAuthorizingRealm.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private SystemAuthorizingRealmUtil obtainSystemAuthorizingRealmUtil() {
		return (SystemAuthorizingRealmUtil) cApplicationContext.getBean("systemAuthorizingRealmUtil");
	}

	private ISystemAuthorizingRealmDao obtainISystemAuthorizingRealmDao() {
		return (ISystemAuthorizingRealmDao) cApplicationContext.getBean("systemAuthorizingRealmDao");
	}

	/**
	 * 授权 To grant authorization Abstract methods from the parent class
	 * 为用户进行授权获取用户的角色和权限信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		/**
		 * 获取用户的所包含的权限和角色信息
		 */

		if (principalCollection != null) {

			String username = (String) principalCollection.getPrimaryPrincipal();
			log.info("获取用户名为{}的用户所具有的角色和权限信息", username);
			IDataTransferObject queryParameters = new DataTransferObject();
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			/**
			 * 从session中获取用户的staffId
			 */
			String staffId = FrameworkStringUtil.asString(session.getAttribute("loginStaffId"));
			queryParameters.evaluteRequiredParameter("staffId", staffId);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			/**
			 * 获取用户的staffId根据staffId查询出用户所具有的所有权限
			 */
			Set<String> permissions = new HashSet<String>();
			try {
				List<JSONObject> permissionItems = obtainISystemAuthorizingRealmDao().obtainAllPermissionsFromDB(queryParameters);
			    if(permissionItems!=null&&permissionItems.size()>0){
			        for(JSONObject permissionItem:permissionItems){
			        	log.info("获取用户名为{}的用户所具有:{}该权限",username, FrameworkStringUtil.asString(permissionItem.get("LIMIT_NAME ")));
			        	permissions.add(FrameworkStringUtil.asString(permissionItem.get("LIMIT_NAME")));
			        }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			info.addStringPermissions(permissions);
			return info;
		}
		return null;

	}

	/**
	 * 认证 login authentication Abstract methods from the parent class
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		log.info("at {} execution authentification of user operation", System.currentTimeMillis());
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
	}

	public boolean isPermitted(PrincipalCollection principals, String permission) {
		/**
		 * permission
		 */
		Stack<String> suffixExpressionItems = obtainSystemAuthorizingRealmUtil().structureSuffixExpression(permission);
		if (suffixExpressionItems.size() == 1) {
			/**
			 * suffixExpression.pop()移除这个堆栈的顶部对象,并将该对象作为这个函数的返回值
			 */
			return super.isPermitted(principals, suffixExpressionItems.pop());
		} else if (suffixExpressionItems.size() > 1) {
			List<String> authorizationSuffixResultSet = new ArrayList<String>();
			/**
			 * 将其中的权限字符串解析成true , false
			 */
			for (String suffixExpressionItem : suffixExpressionItems) {
				if (obtainSystemAuthorizingRealmUtil().getConnectionSymbolSet().contains(suffixExpressionItem)) {
					/**
					 * 逻辑判断连接符"not", "!", "and", "&&", "or", "||"
					 */
					authorizationSuffixResultSet.add(suffixExpressionItem);
				} else {
					/**
					 * 获取当前用户是否包含有权限如果有就为"true"如果没有就为"false"
					 */
					authorizationSuffixResultSet
							.add(Boolean.toString(super.isPermitted(principals, suffixExpressionItem)));
				}
			}
			log.debug(
					"The permissions needed to access the URL are:{}  The permissions suffixExpressionItems:{}  Authorization suffix result set:{}",
					permission, suffixExpressionItems, authorizationSuffixResultSet);
			// 计算逆波兰后缀表达式
			boolean booleanOfSuffixExpression = obtainSystemAuthorizingRealmUtil()
					.computeSuffixExpression(authorizationSuffixResultSet);

			return booleanOfSuffixExpression;
		}
		return false;
	}

	/**
	 * 获取用户的角色和权限
	 */
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		/**
		 * 如果用户没有登陆系统的就直接跳转到登陆页面
		 */
		if (principals == null) {
			log.info("当前用户还没有登陆所以没有获得任何的授权信息");
			return null;
		}
		log.info("获取用户名为{}用户的权限和角色信息", principals);
		if (log.isTraceEnabled()) {
			log.trace("Retrieving AuthorizationInfo for principals [" + principals + "]");
		}
		/**
		 * 从数据库中查找用户所具有的所有权限
		 */
		return doGetAuthorizationInfo(principals);
	}
}
