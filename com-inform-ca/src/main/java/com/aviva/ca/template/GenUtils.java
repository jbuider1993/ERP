package com.inform.ca.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class GenUtils {
	private final static Map<String, String> JavaTypes = new HashMap<>();
	private final static Map<String, String> TsTypes = new HashMap<>();
	private static Set<String> cachePool = new HashSet<>();
	private final static Set<String> baseJavaType = new HashSet<>();
	private final static Map<String, String> objectJavaType = new HashMap<>();

	static {
		JavaTypes.put("string", "String");
		JavaTypes.put("date", "Date");
		JavaTypes.put("datetime", "Date");
		JavaTypes.put("integer", "long");
		JavaTypes.put("int", "long");
		JavaTypes.put("number", "double");
		JavaTypes.put("boolean", "boolean");

		TsTypes.put("string", "string");
		TsTypes.put("date", "string");
		TsTypes.put("datetime", "string");
		TsTypes.put("integer", "number");
		TsTypes.put("int", "number");
		TsTypes.put("number", "number");
		TsTypes.put("boolean", "boolean");
		
		baseJavaType.add("String");
		baseJavaType.add("int");
		baseJavaType.add("char");
		baseJavaType.add("byte");
		baseJavaType.add("short");
		baseJavaType.add("long");
		baseJavaType.add("float");
		baseJavaType.add("double");
		baseJavaType.add("boolean");
		baseJavaType.add("Integer");
		baseJavaType.add("Long");
		baseJavaType.add("Boolean");
		baseJavaType.add("Float");
		baseJavaType.add("Short");
		baseJavaType.add("Double");
		baseJavaType.add("Integer");
		baseJavaType.add("Byte");
		
		objectJavaType.put("Date", "java.util.Date");		
		objectJavaType.put("DateTime", "java.util.DateTime");		
				
	}

	public static boolean isBasicType(String type) {
		return baseJavaType.contains(type);
	}
	
	public static String importClass(String objectType) {
		return objectJavaType.get(objectType);
	}
	
	public static void addElement(String element) {
		cachePool.add(element);
	}
	
	public static List<String> getElements() {
		return new ArrayList<>(cachePool);
	}
	public static void clearElements() {
		cachePool.clear();
	}
	
	public static List<String> getElementsAndClear() {
		List<String> elements = new ArrayList<>(cachePool);
		clearElements();
		return elements;
	}
	
	
	private static String getCombinName(String sourceName) {

		if (StringUtils.isEmpty(sourceName)) {
			return sourceName;
		}
		String[] subNames = sourceName.split("_");
		String retString = "";

		for (String name : subNames) {
			retString = retString + StringUtils.capitalize(StringUtils.lowerCase(name));
		}
		return retString;
	}

	public static String capitalizeTableName(String tableName) {
		return getCombinName(tableName);
	}

	public static String uncapitalizeTableName(String tableName) {
		return StringUtils.uncapitalize(getCombinName(tableName));
	}

	public static String lowercaseTableName(String tableName) {
		return StringUtils.lowerCase(getCombinName(tableName));
	}

	public static String converColumnName(String columnName) {
		return StringUtils.uncapitalize(getCombinName(columnName));
	}

	public static String mapToJavaType(String tempType) {
		String retType = JavaTypes.get(StringUtils.lowerCase(tempType));
		return StringUtils.isEmpty(retType) ? "String" : retType;
	}

	public static String mapToTsType(String tempType) {
		String retType = TsTypes.get(StringUtils.lowerCase(tempType));
		return StringUtils.isEmpty(retType) ? "string" : retType;
	}
}
