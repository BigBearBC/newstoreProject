package com.cy.store.commom.copyUtils.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @date ：Created in 2020-08-04 16:55
 * @description：分页参数返回
 * @modified By：
 * @version: V1.0
 */
@Data
@Accessors(chain = true)
@Builder
public class PageResult<T> implements Serializable {

    private long totalElements;

    private List<T> content;

    public PageResult(long totalElements, List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public PageResult() {
    }
}
