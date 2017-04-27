package com.howard.www.common.web.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.howard.www.core.dao.support.JsonNamedParameterJdbcTemplate;

public class CodeTableLoadListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(context);
		JsonNamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext
				.getBean("namedParameterJdbcTemplate",
						JsonNamedParameterJdbcTemplate.class);
		Map<String, String> param = new HashMap<String, String>();
		List<Map<String, Object>> items = namedParameterJdbcTemplate
				.queryForList("select * from jo_user", param);
		for (Map<String, Object> item : items) {
			Iterator<Map.Entry<String, Object>> entries = item.entrySet()
					.iterator();
			while (entries.hasNext()) {

				Entry<String, Object> entry = entries.next();

				System.out.println("Key = " + entry.getKey() + ", Value = "
						+ entry.getValue());

			}
		}
	}

}
