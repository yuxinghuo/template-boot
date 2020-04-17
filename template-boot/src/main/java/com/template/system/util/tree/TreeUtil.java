package com.template.system.util.tree;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * tree和实体转换
 * @时间：2020-04-17
 * @version V1.0
 */
public final class TreeUtil {

	/**
	 * 1.解析树形数据-注解型 递归算法 实体类需要增加注解 {@link TreeAnnotation}
	 * 
	 * @param <T>             目标参数泛型
	 * @param entityList（数据源）
	 * @return {@link List}
	 */
	public static <T> List<T> toTreeAnnoList(List<T> entityList) {
		if (CollUtil.isEmpty(entityList)) {
			return Collections.emptyList();
		}
		// 校验实体类是否具备解析条件
		Map<TreeEnum, Field> treeMap = TreeEnum.convertTreeMap(entityList.get(0).getClass());
		// 数据整合-根据id 组装成 HashSet
		Set<Serializable> idSet = entityList.stream()
				.map(e -> (Serializable) ReflectUtil.getFieldValue(e, treeMap.get(TreeEnum.ID)))
				.collect(Collectors.toSet());
		// 获取顶层元素集合
		List<T> rootList = entityList.stream().filter(e -> {
			Object parentId = ReflectUtil.getFieldValue(e, treeMap.get(TreeEnum.PARENT_ID));
			return StrUtil.isBlankIfStr(parentId) || !idSet.contains(parentId);
		}).collect(Collectors.toList());
		// 数据整合-根据parentId 组装成 HashMap
		Map<Serializable, List<T>> parentIdMap = entityList.stream().collect(Collectors.groupingBy(e -> {
			Serializable parentId = (Serializable) ReflectUtil.getFieldValue(e, treeMap.get(TreeEnum.PARENT_ID));
			return parentId != null ? parentId : StrUtil.EMPTY;
		}));
		// 递归获取每个顶层元素的子数据集合
		rootList.forEach(r -> recursiveFill(r, treeMap, parentIdMap));
		return rootList;
	}

	/**
	 * 递归填充子集
	 * 
	 * @param root        根对象
	 * @param treeMap     字段属性的必要条件
	 * @param parentIdMap 以parentId 为key的List集合
	 * 
	 */
	public static <T> T recursiveFill(T root, Map<TreeEnum, Field> treeMap, Map<Serializable, List<T>> parentIdMap) {
		Object parentId = ReflectUtil.getFieldValue(root, treeMap.get(TreeEnum.ID));
		Assert.notNull(parentId, "[Id 异常：{}]", root);
		// 循环并获取子集对象
		List<T> children = parentIdMap.get(parentId);
		/*if (children == null) {
			children = Collections.emptyList();
		}*/
		if(children!=null){
			// 递归子集
			children.forEach(s -> recursiveFill(s, treeMap, parentIdMap));
		}
		// 返回
		ReflectUtil.setFieldValue(root, treeMap.get(TreeEnum.SUB_LIST), children);
		return root;
	}

	/**
	 * 将list转为树结构map
	 *
	 * @param entityList   目标集合
	 * @param topId        顶级id
	 * @param idName       id名称
	 * @param parentIdName 父级id名称
	 * @param childrenName 子集名称
	 * @return map集合
	 */
	public static <E> List<Map<String, Object>> toMapTree(List<E> entityList, Serializable topId, String idName,
			String parentIdName, String childrenName) {
		if (CollUtil.isEmpty(entityList)) {
			return Collections.emptyList();
		}
		final List<Map<String, Object>> collect = entityList.stream().map(BeanUtil::beanToMap)
				.collect(Collectors.toList());
		return collect.stream().map(e -> {
			final Object obj = e.get(parentIdName);
			if (StrUtil.isBlankIfStr(obj)) {
				return null;
			}
			final String parentId = obj.toString();
			if (parentId.equals(topId)) {
				getChildren(collect, e, idName, parentIdName, childrenName);
				return e;
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 * 获取字集
	 *
	 * @param entityList   目标集合
	 * @param parentMap    父集合
	 * @param idName       id名
	 * @param parentIdName 父级id名称
	 * @param childrenName 子集名称
	 */
	public static void getChildren(List<Map<String, Object>> entityList, Map<String, Object> parentMap, String idName,
			String parentIdName, String childrenName) {
		final Object id = parentMap.get(idName);
		final List<Map<String, Object>> collect = entityList.stream().map(e -> {
			final Object parentId = e.get(parentIdName);
			if (parentId.equals(id)) {
				getChildren(entityList, e, idName, parentIdName, childrenName);
				return e;
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		parentMap.put(childrenName, collect);
	}

}