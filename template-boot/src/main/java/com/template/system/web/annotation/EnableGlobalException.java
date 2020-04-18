package com.template.system.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.template.comon.exception.ExceptionController;
import com.template.comon.exception.ExceptionControllerAdvice;
import org.springframework.context.annotation.Import;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ //
		ExceptionController.class, // 全局异常处理
		ExceptionControllerAdvice.class, // 全局控制层异常处理
})
public @interface EnableGlobalException {

}
