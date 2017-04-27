package com.howard.www.core.web.security.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.howard.www.core.web.security.cache.manager.FrameworkRedisManager;
import com.howard.www.core.web.security.cache.util.FrameworkSerializeUtils;

public class FrameworkRedisSessionDao extends AbstractSessionDAO {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkRedisSessionDao.class);
	/**
	 * The Redis key prefix for the sessions
	 */
	private String redisSessionKeyPrefix = "shiro_redis_session:";

	private FrameworkRedisManager frameworkRedisManager;

	public String getRedisSessionKeyPrefix() {
		return redisSessionKeyPrefix;
	}

	public void setRedisSessionKeyPrefix(String redisSessionKeyPrefix) {
		this.redisSessionKeyPrefix = redisSessionKeyPrefix;
	}

	public FrameworkRedisManager getFrameworkRedisManager() {
		return frameworkRedisManager;
	}

	public void setFrameworkRedisManager(
			FrameworkRedisManager frameworkRedisManager) {
		this.frameworkRedisManager = frameworkRedisManager;
		frameworkRedisManager.initializationRedisConnection();
	}

	@Override
	public void delete(Session paramSession) {
		if (paramSession == null || paramSession.getId() == null) {
			log.error("session or session id is null");
			return;
		}
		try{
			frameworkRedisManager.del(this.getByteKey(paramSession.getId()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();

		Set<byte[]> keys = frameworkRedisManager
				.keys(this.redisSessionKeyPrefix + "*");
		if (keys != null && keys.size() > 0) {
			for (byte[] key : keys) {
				Session s = (Session) FrameworkSerializeUtils
						.deserialize(frameworkRedisManager.get(key));
				sessions.add(s);
			}
		}

		return sessions;
	}

	@Override
	public void update(Session paramSession) throws UnknownSessionException {
		this.saveSession(paramSession);
	}

	@Override
	protected Serializable doCreate(Session paramSession) {
		Serializable sessionId = this.generateSessionId(paramSession);
		this.assignSessionId(paramSession, sessionId);
		this.saveSession(paramSession);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable paramSerializable) {
		if (paramSerializable == null) {
			log.error("session id is null");
			return null;
		}
		Session session = (Session) FrameworkSerializeUtils
				.deserialize(frameworkRedisManager.get(this
						.getByteKey(paramSerializable)));
		return session;
	}

	/**
	 * save session
	 * 
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			log.error("session or session id is null");
			return;
		}
		byte[] key = getByteKey(session.getId());
		byte[] value = FrameworkSerializeUtils.serialize(session);
		session.setTimeout(frameworkRedisManager.getExpire() * 1000);
		this.frameworkRedisManager.set(key, value,
				frameworkRedisManager.getExpire());
	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(Serializable sessionId) {
		String preKey = this.redisSessionKeyPrefix + sessionId;
		return preKey.getBytes();
	}
}
