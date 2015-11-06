package com.offering.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) @Target({FIELD})
public @interface Column {

	/**
     * 数据库列名
     * <p> If the value is "##default", then element name is derived from the
     * JavaBean property name.
     */
	 String name() default "##default";
}
