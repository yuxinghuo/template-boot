package com.template.system.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.template.comon.entity.TreeEntity;
import com.template.comon.exception.BusinessException;
import com.template.system.util.tree.TreeEnum;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EntityUtil {
    public EntityUtil() {
    }

    public static void putAnnotationValue(Annotation annotation, String k, Object v) {
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field value = invocationHandler.getClass().getDeclaredField("memberValues");
            value.setAccessible(true);
            Map<String, Object> memberValues = (Map)value.get(invocationHandler);
            memberValues.put(k, v);
        } catch (Exception var6) {
            throw new BusinessException(ExceptionUtil.getMessage(var6));
        }
    }

    public static void setPrivateFinalField(Object obj, String fieldName, Object value) throws Exception {
        Field field = ReflectUtil.getField(obj instanceof Class ? (Class)obj : obj.getClass(), fieldName);
        field.setAccessible(true);
        Field modifersField = Field.class.getDeclaredField("modifiers");
        modifersField.setAccessible(true);
        modifersField.setInt(field, field.getModifiers() & -17);
        field.set(obj, value);
    }

    public static void leftDuplicateRemoval(Object left, Object right) {
        Field[] fields = ReflectUtil.getFields(left.getClass());
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            Object leftVal = BeanUtil.getFieldValue(left, field.getName());
            Object rightVal = BeanUtil.getFieldValue(right, field.getName());
            if (!"serialVersionUID".equals(field.getName()) && ObjectUtil.isNotNull(leftVal) && leftVal.equals(rightVal)) {
                BeanUtil.setFieldValue(left, field.getName(), (Object)null);
            }
        }

    }

    public static <T, S> List<T> listConver(List<S> source, Class<T> clazz) {
        if (ObjectUtil.isNull(clazz)) {
            throw new BusinessException("class is null");
        } else {
            return !CollUtil.isEmpty(source) ? (List)source.stream().map((s) -> {
                return BeanUtil.toBean(s, clazz);
            }).collect(Collectors.toList()) : Collections.emptyList();
        }
    }

    public static <E extends TreeEntity<E>> List<E> toTreeList(List<E> entityList, Serializable topId) {
        if (CollUtil.isEmpty(entityList)) {
            return Collections.emptyList();
        } else {
            List<E> resultList = (List)entityList.stream().filter((e) -> {
                return e.getParentId() == null || e.getParentId().equals(topId);
            }).collect(Collectors.toList());
            resultList.forEach((r) -> {
                recursiveFill(entityList, r);
            });
            return resultList;
        }
    }

    public static <T> List<T> toTreeAnnoList(List<T> entityList, Serializable topId) {
        if (CollUtil.isEmpty(entityList)) {
            return Collections.emptyList();
        } else {
            Map<TreeEnum, Field> treeMap = TreeEnum.convertTreeMap(entityList.get(0).getClass());
            List<T> resultList = (List)entityList.stream().filter((e) -> {
                return ReflectUtil.getFieldValue(e, (Field)treeMap.get(TreeEnum.PARENT_ID)) == null || ReflectUtil.getFieldValue(e, (Field)treeMap.get(TreeEnum.PARENT_ID)).equals(topId);
            }).collect(Collectors.toList());
            resultList.forEach((r) -> {
                recursiveFill(entityList, r, treeMap);
            });
            return resultList;
        }
    }

    public static <E extends TreeEntity<E>> E recursiveFill(List<E> entityList, E root) {
        Object parentId = root.getId();
        Assert.notNull(parentId, "[Id 异常：{}]", new Object[]{root});
        List<E> subList = (List)entityList.stream().filter((e) -> {
            return parentId.equals(e.getParentId());
        }).collect(Collectors.toList());
        subList.forEach((s) -> {
            recursiveFill(entityList, s);
        });
        root.setSubList(subList);
        return root;
    }

    public static void recursiveFill(List<?> entityList, Object root, Map<TreeEnum, Field> treeMap) {
        Object parentId = ReflectUtil.getFieldValue(root, (Field)treeMap.get(TreeEnum.ID));
        Assert.notNull(parentId, "[Id 异常：{}]", new Object[]{root});
        List<?> subList = (List)entityList.stream().filter((e) -> {
            return parentId.equals(ReflectUtil.getFieldValue(e, (Field)treeMap.get(TreeEnum.PARENT_ID)));
        }).collect(Collectors.toList());
        subList.forEach((s) -> {
            recursiveFill(entityList, s, treeMap);
        });
        ReflectUtil.setFieldValue(root, (Field)treeMap.get(TreeEnum.SUB_LIST), subList);
    }

    public static <E> List<Map<String, Object>> toMapTree(List<E> entityList, Serializable topId, String idName, String parentIdName, String childrenName) {
        if (CollUtil.isEmpty(entityList)) {
            return Collections.emptyList();
        } else {
            List<Map<String, Object>> collect = (List)entityList.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
            return (List)collect.stream().map((e) -> {
                Object obj = e.get(parentIdName);
                if (StrUtil.isBlankIfStr(obj)) {
                    return null;
                } else {
                    String parentId = obj.toString();
                    if (parentId.equals(topId)) {
                        getChildren(collect, e, idName, parentIdName, childrenName);
                        return e;
                    } else {
                        return null;
                    }
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public static void getChildren(List<Map<String, Object>> entityList, Map<String, Object> parentMap, String idName, String parentIdName, String childrenName) {
        Object id = parentMap.get(idName);
        List<Map<String, Object>> collect = (List)entityList.stream().map((e) -> {
            Object parentId = e.get(parentIdName);
            if (parentId.equals(id)) {
                getChildren(entityList, e, idName, parentIdName, childrenName);
                return e;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        parentMap.put(childrenName, collect);
    }
}
