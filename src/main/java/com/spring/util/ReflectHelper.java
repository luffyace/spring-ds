package com.spring.util;

import java.lang.annotation.Annotation;

public class ReflectHelper {
	
	/**
     * 获取类上的注解
     * @param targetAnnotationClass 目标注解
     * @param targetObjcetClass     目标类
     * @return 目标注解实例
     */
    public static Annotation getClassAnnotation(Class targetAnnotationClass, Class<?> targetObjcetClass) {
        Annotation methodAnnotation = targetObjcetClass.getAnnotation(targetAnnotationClass);
        return methodAnnotation;
    }

    /**
     * 获取类上的注解
     * @return 目标注解实例
     */
    public static Annotation getClassAnnotation(Class targetAnnotationClass, Object obj) {
        return getClassAnnotation(targetAnnotationClass, obj.getClass());
    }
}
