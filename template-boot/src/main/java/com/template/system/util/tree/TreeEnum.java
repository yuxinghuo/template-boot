package com.template.system.util.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形结构
 * @时间：2020-04-17
 * @version V1.0
 */
public enum TreeEnum {
	/**
	 * 唯一标识
	 */
	ID,
	/**
	 * 父ID
	 */
	PARENT_ID,
	/**
	 * 子集
	 */
	SUB_LIST;
	/**
	 * 转换map
	 * 
	 * @param beanType bean类型
	 * @return Map
	 */
	public static Map<TreeEnum, Field> convertTreeMap(Class<?> beanType) {
		// 获取属性
		List<Field> list = CollUtil.toList(ReflectUtil.getFields(beanType));
		Assert.notNull(list, "[Class:{} not has Field]", beanType);
		// 获取注解
		Map<TreeAnnotation, List<Field>> treeMap = list.stream()
				.filter(f -> f.isAnnotationPresent(TreeAnnotation.class))
				.collect(Collectors.groupingBy(f -> f.getAnnotation(TreeAnnotation.class)));
		// 断言数量是否超出
		treeMap.forEach((k, v) -> {
			Assert.isTrue(v.size() == 1, "[@TreeAnnotation(TreeEnum.{})数量超过 1]", k.value());
		});
		// 断言数量是否符合
		Assert.isTrue(TreeEnum.values().length == treeMap.size(), "[请确认:{} 是否全部包含 TreeAnnotation({})]", beanType,
				TreeEnum.values());
		// 返回
		return treeMap.keySet().stream().collect(Collectors.toMap(TreeAnnotation::value, k -> treeMap.get(k).get(0)));
	}

}
