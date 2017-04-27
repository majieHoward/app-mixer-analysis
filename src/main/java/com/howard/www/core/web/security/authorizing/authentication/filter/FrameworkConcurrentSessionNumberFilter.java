package com.howard.www.core.web.security.authorizing.authentication.filter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.howard.www.core.web.security.cache.FrameworkRedisCache;
import com.howard.www.core.web.security.cache.manager.FrameworkRedisCacheManager;

/**
 * 
 * @author howard
 * 
 */
public class FrameworkConcurrentSessionNumberFilter extends AccessControlFilter {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkConcurrentSessionNumberFilter.class);
	private String urlAddressOfkickedOut = "/analysisadmin/analysis/kickout.html";

	private SessionManager sessionManager;

	private FrameworkRedisCacheManager frameworkRedisCacheManager;

	private FrameworkRedisCache<String, List<Serializable>> cache;

	private String kickoutUrl; // 踢出后到的地址

	private boolean kickoutAfter = true;

	private int maximumAllowedLoginOfTheSameUser = 1;

	public String getUrlAddressOfkickedOut() {
		return urlAddressOfkickedOut;
	}

	public void setUrlAddressOfkickedOut(String urlAddressOfkickedOut) {
		this.urlAddressOfkickedOut = urlAddressOfkickedOut;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public FrameworkRedisCacheManager getFrameworkRedisCacheManager() {
		return frameworkRedisCacheManager;
	}

	public void setFrameworkRedisCacheManager(
			FrameworkRedisCacheManager frameworkRedisCacheManager) {
		this.frameworkRedisCacheManager = frameworkRedisCacheManager;
		try {
			cache = frameworkRedisCacheManager
					.obtainFrameworkRedisCache("shiro_redis_session");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMaximumAllowedLoginOfTheSameUser() {
		return maximumAllowedLoginOfTheSameUser;
	}

	public void setMaximumAllowedLoginOfTheSameUser(
			int maximumAllowedLoginOfTheSameUser) {
		this.maximumAllowedLoginOfTheSameUser = maximumAllowedLoginOfTheSameUser;
	}

	protected boolean isAccessAllowed(ServletRequest paramServletRequest,
			ServletResponse paramServletResponse, Object paramObject)
			throws Exception {
		return false;
	}

	protected boolean onAccessDenied(ServletRequest paramServletRequest,
			ServletResponse paramServletResponse) throws Exception {
		Subject subject = getSubject(paramServletRequest, paramServletResponse);
		if (!subject.isAuthenticated()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}
		Session session = subject.getSession();
		String userName = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();
		// TODO 同步控制
		List<Serializable> deque = cache.get(userName);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
		}
		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId)
				&& session.getAttribute("kickout") == null) {
			deque.add(sessionId);
		}

		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maximumAllowedLoginOfTheSameUser) {
			Serializable kickoutSessionId = null;
			if (kickoutAfter) { // 如果踢出后者
				kickoutSessionId = deque.remove(0);
			} else { // 否则踢出前者
				kickoutSessionId = deque.remove(deque.size() - 1);
			}
			try {
				Session kickoutSession = sessionManager
						.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e) {// ignore exception
			}
		}
		cache.put(userName, deque);
		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if (session.getAttribute("kickout") != null) {
			// 会话被踢出了
			try {
				subject.logout();
			} catch (Exception e) { // ignore
			}
			saveRequest(paramServletRequest);
			WebUtils.issueRedirect(paramServletRequest, paramServletResponse,
					kickoutUrl);
			return false;
		}
		return true;
	}

}
