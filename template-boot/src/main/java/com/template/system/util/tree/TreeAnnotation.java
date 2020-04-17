package com.template.system.util.tree;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 树形数据解析
 *
 * @时间：2020-04-17
 * @version V1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TreeAnnotation {
	/**
	 * 类型
	 * 
	 * @return {@link TreeEnum}
	 */
	TreeEnum value();

}
