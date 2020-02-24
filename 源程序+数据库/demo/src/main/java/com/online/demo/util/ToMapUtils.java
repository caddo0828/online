package com.online.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
/**
 * map类型的操作方法
 * @author LJY
 */
@SuppressWarnings("rawtypes")
public class ToMapUtils {
	
	/**
	 * 实体类转map
	 * @param obj
	 * @return
	 */
	public static Map<String,Object> ObjectToMap(Object obj){
		Map<String,Object> map=new HashMap<String,Object>();
		if(obj==null)
			return map;
		Class cls=obj.getClass();
		Field[] fields=cls.getDeclaredFields();
		try {
			for(Field field:fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * map转实体类
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
		if (map == null) {
			return null;
		}
		Object obj = null;
		try {
			obj = clazz.newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
				}
				field.setAccessible(true);
				field.set(obj,map.get(field.getName()));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	  	return obj;
	}
	/**
	 * 将request请求中的数据格式Map<String,String[]>转换成常用的Map<String,Object>
	 * String[]将以split隔开拼接
	 * @param mapArray
	 * @return
	 */
	public static Map<String,Object> mapStringArrayToObject(Map<String,String[]> mapArray,String split){
		Map<String,Object> map=new HashMap<String,Object>();
		for(String key:mapArray.keySet()){
			String[] tempValue=mapArray.get(key);
			if(tempValue.length==0)
				map.put(key, null);
			else
				map.put(key, StringArrayToString(tempValue,split));
		}
		return map;
	}
	
	public static String StringArrayToString(String [] str,String split){
		StringBuffer sb=new StringBuffer();
		for(String s:str){
			sb.append(s+split);
		}
		return sb.substring(0, sb.length()-1);
	}
	public static void main(String[] args) {
		String[] str=new String[]{"asd","dasad"};
		System.out.println(StringArrayToString(str,"#"));
	}
}
