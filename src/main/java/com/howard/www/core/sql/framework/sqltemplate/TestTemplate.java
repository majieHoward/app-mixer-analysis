package com.howard.www.core.sql.framework.sqltemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTemplate {

	public void testIf() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select * from user_#{demo} where <if test='id != null ' > id  = #{id} </if>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		// map.put("id", "11");
		map.put("demo", "2019");
		SqlMeta process = template.process(map);
		System.out.println(process.getSql());
	}

	public void testWhere() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select * from user <where> <if test='id != null ' > and id  = #{id} </if>  <if test=' name != null' >name =#{name}</if> </where>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("name", "1fffdsfdf1");

		SqlMeta process = template.process(map);

		System.out.println(process);
	}

	public void testSet() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("update user  <set> <if test='id != null '> id = #{id} ,</if><if test='name != null '> name = #{name} ,</if> </set> ");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "123");
		map.put("name", "1fffdsfdf1");

		SqlMeta process = template.process(map);

		System.out.println(process);

	}

	public void testChoose() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select  * from user <where><choose><when test=' id!= null '> and id = #{id} </when><when test=' name!= null '> and name = #{name} </when></choose> </where>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "123");
		map.put("name", "hhh1");

		SqlMeta process = template.process(map);

		System.out.println(process);

	}

	public void testForEach() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select  * from user <where> id in <foreach item=\"item\" index=\"index\" collection=\"list\"    open=\"(\" separator=\",\" close=\")\">   ${item}   ${index}  </foreach></where>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		// map.put("id", "123");
		// map.put("name", "hhh1");

		/*
		 * ArrayList<String> arrayList = new ArrayList<String>() ;
		 * 
		 * arrayList.add("1") ; arrayList.add("2") ; arrayList.add("3") ;
		 * arrayList.add("4") ;
		 */

		HashMap<String, Object> map2 = new HashMap<String, Object>();

		map2.put("11", "11-11");
		map2.put("22", "22-22");

		map.put("list", map2);

		SqlMeta process = template.process(map);

		System.out.println(process);

	}

	public static void main(String[] args) {
       new TestTemplate().testIf();
	}
}
