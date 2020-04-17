package com.template.system.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * cron表达式
	 */
	private String cronExpression;

	/**
	 * 任务调用的方法名
	 */
	private String methodName;

	/**
	 * 任务是否有状态
	 */
	private String isConcurrent;

	/**
	 * 任务描述
	 */
	private String description;

	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanClass;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	private Date createDate;

	/**
	 * 任务状态
	 */
	private String jobStatus;

	/**
	 * 任务分组
	 */
	private String jobGroup;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	private Date updateDate;

	/**
	 * 任务名
	 */
	private String jobName;

}
