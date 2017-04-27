package com.howard.www.core.sql.framework.sqltemplate.script;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author howard
 * 
 */
public class SetFragment extends TrimFragment {

	private static List<String> suffixList = Arrays.asList(",");

	public SetFragment(SqlFragment contents) {
		super(contents, "SET", null, null, suffixList);
	}

}
