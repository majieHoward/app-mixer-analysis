package com.howard.www.core.web.security.authorizing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository("systemAuthorizingRealmUtil")
public class SystemAuthorizingRealmUtil implements InitializingBean {
	protected final Logger log = LoggerFactory
			.getLogger(SystemAuthorizingRealmUtil.class);
	/**
	 * connectionSymbol
	 */
	private Map<String, Integer> connectionSymbol = new HashMap<String, Integer>();

	private Set<String> connectionSymbolSet;

	public Set<String> getConnectionSymbolSet() {
		return connectionSymbolSet;
	}

	public void setConnectionSymbolSet(Set<String> connectionSymbolSet) {
		this.connectionSymbolSet = connectionSymbolSet;
	}

	public Map<String, Integer> getConnectionSymbol() {
		return connectionSymbol;
	}

	public void setConnectionSymbol(Map<String, Integer> connectionSymbol) {
		this.connectionSymbol = connectionSymbol;
	}

	public void initConnectionSymbolItem() {
		this.connectionSymbol.clear();
		/**
		 * paramSymbolItems
		 */
		String[] paramSymbolItems = { "not", "0", "!", "0", "and", "0", "&&",
				"0", "or", "0", "||", "0" };
		if (paramSymbolItems.length > 0 && (paramSymbolItems.length) % 2 == 0) {
			for (int i = 0; i < paramSymbolItems.length; i = i + 2) {
				connectionSymbol.put(paramSymbolItems[i],
						Integer.parseInt(paramSymbolItems[i + 1]));
			}
		}
		this.connectionSymbolSet = connectionSymbol.keySet();
	}

	public void afterPropertiesSet() throws Exception {
		initConnectionSymbolItem();
	}

	public boolean computeSuffixExpression(
			Collection<String> authorizationSuffixResultSet) {
		log.debug("Authorization suffix result set :{}",
				authorizationSuffixResultSet);
		Stack<Boolean> authorizationSuffixExpressionstack = new Stack<>();
		for (String authorizationSuffixResult : authorizationSuffixResultSet) {
			Boolean paramOne, paramTwo;
			if (connectionSymbolSet.contains(authorizationSuffixResult)) {
				if ("!".equals(authorizationSuffixResult)
						|| "not".equals(authorizationSuffixResult)) {
					authorizationSuffixExpressionstack
							.push(!authorizationSuffixExpressionstack.pop());
				} else if ("and".equals(authorizationSuffixResult)
						|| "&&".equals(authorizationSuffixResult)) {
					paramOne = authorizationSuffixExpressionstack.pop();
					paramTwo = authorizationSuffixExpressionstack.pop();
					authorizationSuffixExpressionstack.push(paramOne
							&& paramTwo);
				} else {
					paramOne = authorizationSuffixExpressionstack.pop();
					paramTwo = authorizationSuffixExpressionstack.pop();
					authorizationSuffixExpressionstack.push(paramOne
							|| paramTwo);
				}
			} else {
				authorizationSuffixExpressionstack.push(Boolean
						.parseBoolean(authorizationSuffixResult));
			}
		}
		if (authorizationSuffixExpressionstack.size() > 1) {
			log.error("computeRpn RESULT ERROR>{}  exp:{}",
					authorizationSuffixExpressionstack,
					authorizationSuffixResultSet);
			throw new RuntimeException("compute error！ stack: "
					+ authorizationSuffixResultSet.toString());
		} else {
			log.debug("computeRpn RESULT SUCCESS>{}",
					authorizationSuffixExpressionstack);
			return authorizationSuffixExpressionstack.pop();
		}
	}

	/**
	 * 构造逆波兰表达式
	 * 
	 * @param connectionSymbolSetItems
	 * @param permission
	 * @return
	 */
	public Stack<String> structureSuffixExpression(String permission) {
		Stack<String> connectionSymbolStrack = new Stack<>();
		Stack<String> suffixExpression = new Stack<>();
		for (String str : permission.split(" ")) {
			/**
			 * 
			 */
			str = str.trim();
			String strL = str.toLowerCase();
			if ("".equals(str)) {
				continue;
			}
			if ("(".equals(str)) {
				// 左括号
				connectionSymbolStrack.push(str);
			} else if (")".equals(str)) {
				// 右括号
				while (!connectionSymbolStrack.empty()) {
					String temp = connectionSymbolStrack.pop();
					if ("(".equals(temp)) {
						break;
					} else {
						suffixExpression.push(temp);
					}
				}
			} else if (this.connectionSymbolSet.contains(strL)) {
				// 操作符
				if (connectionSymbolStrack.empty()) {
					connectionSymbolStrack.push(strL);
				} else {
					/**
					 * connectionSymbolStrack peek()方法 查看栈顶对象而不移除它
					 */
					String temp = connectionSymbolStrack.peek();
					if ("(".equals(temp) || ")".equals(temp)) {
						connectionSymbolStrack.push(strL);
					} else if (this.connectionSymbol.get(strL) >= this.connectionSymbol
							.get(temp)) {
						connectionSymbolStrack.push(strL);
					} else {
						suffixExpression.push(connectionSymbolStrack.pop());
						connectionSymbolStrack.push(strL);
					}
				}
			} else {
				// 运算数
				suffixExpression.push(str);
			}
		}
		while (!connectionSymbolStrack.empty()) {
			suffixExpression.push(connectionSymbolStrack.pop());
		}
		return suffixExpression;
	}
}
