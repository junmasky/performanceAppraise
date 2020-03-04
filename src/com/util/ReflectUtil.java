package com.util;
 
import java.lang.reflect.Field;
 
 
/**
 * 反射工具类
 * @author SCY
 *
 * @createDate 2013-12-19上午09:29:45
 * 
 * @version 1.0
 */
public class ReflectUtil {
	
	private ReflectUtil(){
		
	}
	
	
	/**
	 * 反射获取对象String类型属性值
	 * @param obj
	 * @param paramName
	 * @return String 对象属性值
	 * @throws Exception
	 */
	public static String getObjValue(Object obj, String paramName)
			throws Exception{
		
		String getMethodName = "get"
				+ Character.toUpperCase(paramName.charAt(0))
				+ paramName.substring(1);
		return (String) obj.getClass().getMethod(getMethodName).invoke(obj);
	}
 
	/**
	 * 为对象某String类型属性设置指定值
	 * @param obj 待赋值对象
	 * @param paramName  属性名称
	 * @param paramValue 属性值
	 * @throws Exception
	 */
	public static void setObjValue(Object obj, String paramName,
			String paramValue) throws Exception {
		
		String getMethodName = "set"
				+ Character.toUpperCase(paramName.charAt(0))
				+ paramName.substring(1);
		obj.getClass().getMethod(getMethodName, String.class).invoke(obj,
				paramValue);
	}
	
	 /**
     * 利用反射获取指定对象的指定属性
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
     */
    public static Object getFieldValue(Object obj, String fieldName) throws IllegalArgumentException, IllegalAccessException {
        Object result = null;
        final Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
			field.setAccessible(true);
			result = field.get(obj);
        }
        return result;
    }
 
    /**
     * 利用反射获取指定对象里面的指定属性
     */
    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        return field;
    }
 
    /**
     * 利用反射设置指定对象的指定属性为指定的值
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static void setFieldValue(Object obj, String fieldName, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
        final Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
        	field.setAccessible(true);
            field.set(obj, fieldValue);
        }
    }
}
