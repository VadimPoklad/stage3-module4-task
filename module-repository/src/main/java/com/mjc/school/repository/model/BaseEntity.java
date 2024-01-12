package com.mjc.school.repository.model;

public interface BaseEntity<T> {
    T getId();
    void setId(T id);
}
