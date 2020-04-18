package com.template.comon.base;



import cn.hutool.core.util.StrUtil;
import com.template.comon.constant.ExceptionConstant;
import com.template.comon.constant.ResultCodeEnum;

public interface BaseConvert extends BaseWirter {
	/**
	 * 退出
	 * @param error 异常信息
	 */
	default void exit(String error) {
		if (StrUtil.isBlank(error)) {
			error = ExceptionConstant.ERROR;
		}
		wirteJsonObject(ResultCodeEnum.CODE_500.code(), error);
		exit();
	}

}
