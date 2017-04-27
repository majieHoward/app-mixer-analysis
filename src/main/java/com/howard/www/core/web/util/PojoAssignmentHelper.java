package com.howard.www.core.web.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 
 * @author howard
 * 
 */
public class PojoAssignmentHelper {
	/**
	 * 通过JavaBean中的get方法获得JavaBean中的某个属性的值
	 * 
	 * @param paramPlatformXmlBean
	 * @return
	 * @throws Exception
	 */
	public static String obtainOneOfParameterValues(Object originalPojoBeanObject,
			String methodName, Object methodParameter) throws Exception {
		if (methodName == null || originalPojoBeanObject == null) {
			return "";
		}
		// eventIdentification 得到方法名获得具体的方法
		Method methodOfIsEventIdentification = originalPojoBeanObject
				.getClass().getMethod(methodName);
		if (methodOfIsEventIdentification == null) {
			return null;
		}
		if (methodParameter == null) {
			Object isEventIdentification = methodOfIsEventIdentification
					.invoke(originalPojoBeanObject, null);
			String paramIsEvent = FrameworkStringUtil
					.asString(isEventIdentification);
			return paramIsEvent;
		}
		return null;
	}

	/**
	 * 执行JAVABEAN中的Set方法为某个属性赋值
	 * 
	 * @param paramPlatformXmlBean
	 * @param paramName
	 * @param methodParameter
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void evaluateOneOfValueToParameter(Object originalPojoBeanObject,
			String paramName, Object methodParameter) throws Exception {
		Class<?> clazz = null;
		clazz = originalPojoBeanObject.getClass();
		// 使用符合JavaBean规范的属性访问器
		PropertyDescriptor pd = new PropertyDescriptor(paramName, clazz);
		// 调用setter
		Method writeMethod = pd.getWriteMethod();

		Class[] parameterTypes = writeMethod.getParameterTypes();
		/**
		 * 如果是基本数据类型时（如int、float、double、byte、char、boolean）
		 * 需要先将Object转换成相应的封装类之后再转换成对应的基本数据类型 否则会报 ClassCastException
		 **/
		if (parameterTypes[0] == int.class) {
			writeMethod.invoke(originalPojoBeanObject,
					Integer.parseInt(methodParameter.toString()));

		} else if (parameterTypes[0] == float.class) {
			writeMethod.invoke(originalPojoBeanObject,
					((Float) methodParameter).floatValue());

		} else if (parameterTypes[0] == double.class) {
			writeMethod.invoke(originalPojoBeanObject,
					((Double) methodParameter).doubleValue());

		} else if (parameterTypes[0] == byte.class) {
			writeMethod.invoke(originalPojoBeanObject,
					((Byte) methodParameter).byteValue());

		} else if (parameterTypes[0] == char.class) {
			writeMethod.invoke(originalPojoBeanObject,
					((Character) methodParameter).charValue());

		} else if (parameterTypes[0] == boolean.class) {
			writeMethod.invoke(originalPojoBeanObject,
					((Boolean) methodParameter).booleanValue());

		} else {
			writeMethod.invoke(originalPojoBeanObject, methodParameter);
		}

	}
}
