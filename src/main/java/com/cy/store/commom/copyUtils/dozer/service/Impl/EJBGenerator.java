package com.cy.store.commom.copyUtils.dozer.service.Impl;

import com.cy.store.commom.copyUtils.domain.PageResult;
import com.cy.store.commom.copyUtils.dozer.service.IGenerator;
import com.cy.store.commom.copyUtils.web.Paging;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date ：Created in 2019/10/9 10:43
 * @description：dozer实现类
 * @modified By：
 * @version: 1.0
 */
@Component
@Lazy(true)
public class EJBGenerator implements IGenerator {

    @Autowired
    protected Mapper dozerMapper;

    @Override
    public <T, S> T convert(final S s, Class<T> clz) {
        //检测实体类中是否存在LocalDatetime的值，如果有处理一下
        return s == null ? null : this.dozerMapper.map(s, clz);
    }

    @Override
    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        return s == null ? null : s.stream().map(vs -> convert(vs, clz)).collect(Collectors.toList());
    }

    @Override
    public <T, S> Paging<T> convertPaging(Paging<S> paging, Class<T> clz) {
        Paging<T> pagingVo = new Paging<T>();
        pagingVo.setRecords(convert(paging.getRecords(), clz));
        pagingVo.setTotal(paging.getTotal());
        return pagingVo;
    }

    @Override
    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        return s == null ? null : s.stream().map(vs -> convert(vs, clz)).collect(Collectors.toSet());
    }

    @Override
    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = convert(s[i], clz);
        }
        return arr;
    }

    @Override
    public <T, S> PageResult<T> convertPageInfo(PageInfo<S> s, Class<T> clz) {
        return new PageResult(s.getTotal(), convert(s.getList(), clz));
    }
}
