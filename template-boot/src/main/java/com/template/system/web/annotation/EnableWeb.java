package com.template.system.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.template.system.web.config.ControllerConfig;
import com.template.system.web.config.WebMvcConfig;
import org.springframework.context.annotation.Import;



/**
 * web服务通用配置
 * @author 薛超
 * @since 2019年8月1日
 * @version 1.0.5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ //
		ControllerConfig.class, // controller 拦截和vo转换
		WebMvcConfig.class,// web资源-参数处理
})
@EnableSpring // 核心配置
@EnableValidator // 参数校验
public @interface EnableWeb {

}
