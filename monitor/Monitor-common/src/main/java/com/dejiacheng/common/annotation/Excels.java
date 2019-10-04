package com.dejiacheng.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pcy
 * Excel注解集
 * @version 创建时间:2019年9月5日-上午9:30:52
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels
{
    Excel[] value();
}