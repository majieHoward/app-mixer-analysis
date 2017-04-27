package com.howard.www.core.web.security.authorizing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.web.security.authentication.service.IFrameworkAuthenticationInfoService;
import com.howard.www.core.web.security.authorizing.saltencryption.IFrameworkSaltEncryption;

/**
 * 
 * @author howard
 * 
 */
public class SystemAuthorizingRealmOfCache extends AuthorizingRealm {
	protected final Logger log = LoggerFactory.getLogger(SystemAuthorizingRealmOfCache.class);
	@Autowired
	private ApplicationContext cApplicationContext;

	private IFrameworkAuthenticationInfoService obtainFrameworkAuthenticationInfo() {
		return (IFrameworkAuthenticationInfoService) cApplicationContext.getBean("");
	}

	private IFrameworkSaltEncryption obtainFrameworkSaltEncryption() {
		return (IFrameworkSaltEncryption) cApplicationContext.getBean("");
	}

	private SystemAuthorizingRealmUtil obtainSystemAuthorizingRealmUtil() {
		return (SystemAuthorizingRealmUtil) cApplicationContext.getBean("systemAuthorizingRealmUtil");
	}

	/**
	 * 授权 To grant authorization Abstract methods from the parent class
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		/**
		 * 获取用户的所包含的权限和角色信息
		 */
		if (principalCollection != null) {
			String username = (String) principalCollection.getPrimaryPrincipal();
			log.info("at {} execution user {} authorization operation", username, System.currentTimeMillis());
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			/**
			 * Get the role of the user
			 */
			info.addRole("admin");
			Set<String> permissions = new HashSet<String>();
			permissions.add("demo:ddd");
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
		if (principals == null) {
			return null;
		}
		log.info("获取用户名为{}用户的权限和角色信息", principals);
		AuthorizationInfo info = null;

		if (log.isTraceEnabled()) {
			log.trace("Retrieving AuthorizationInfo for principals [" + principals + "]");
		}
		/**
		 * 首先从session中查找
		 */
		Cache cache = getAuthorizationCache();
		if (cache != null) {
			if (log.isTraceEnabled()) {
				log.trace("Attempting to retrieve the AuthorizationInfo from cache.");
			}
			Object key = getAuthorizationCacheKey(principals);
			info = (AuthorizationInfo) cache.get(key);
			if (log.isTraceEnabled()) {
				if (info == null)
					log.trace("No AuthorizationInfo found in cache for principals [" + principals + "]");
				else {
					log.trace("AuthorizationInfo found in cache for principals [" + principals + "]");
				}
			}

		}
		/**
		 * 如果session中查询不到就从数据库中查找
		 */
		if (info == null) {
			info = doGetAuthorizationInfo(principals);

			if ((info != null) && (cache != null)) {
				if (log.isTraceEnabled()) {
					log.trace("Caching authorization info for principals: [" + principals + "].");
				}
				Object key = getAuthorizationCacheKey(principals);
				cache.put(key, info);
			}

		}
		return info;
	}
}
