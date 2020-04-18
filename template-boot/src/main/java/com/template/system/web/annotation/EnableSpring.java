package com.template.system.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.template.system.web.config.SpringConfig;
import org.springframework.context.annotation.Import;


/**
 * 全局的Spring配置核心配置
 * 
 * @author 薛超
 * @since 2019年12月27日
 * @version 1.0.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpringConfig.class)
public @interface EnableSpring {

}
