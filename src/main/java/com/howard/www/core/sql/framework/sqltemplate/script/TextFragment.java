package com.howard.www.core.sql.framework.sqltemplate.script;

import com.howard.www.core.sql.framework.sqltemplate.Context;
import com.howard.www.core.sql.framework.sqltemplate.token.GenericTokenParser;
import com.howard.www.core.sql.framework.sqltemplate.token.TokenHandler;
/**
 * 
 * @author howard
 *
 */
public class TextFragment implements SqlFragment {

	private String sql;

	public TextFragment(String sql) {
		this.sql = sql;
	}

	public boolean apply(final Context context) {

		GenericTokenParser parser2 = new GenericTokenParser("${", "}",
				new TokenHandler() {

					public String handleToken(String content) {

						Object value = OgnlCache.getValue(content,
								context.getBinding());

						return value == null ? "" : value.toString();
					}
				});

		context.appendSql(parser2.parse(sql));
		return true;
	}

}
