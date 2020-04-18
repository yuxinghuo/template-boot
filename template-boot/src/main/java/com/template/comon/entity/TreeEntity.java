package com.template.comon.entity;

import java.io.Serializable;
import java.util.List;

public interface TreeEntity<E> {
    Serializable getId();

    Serializable getParentId();

    void setSubList(List<E> subList);
}
