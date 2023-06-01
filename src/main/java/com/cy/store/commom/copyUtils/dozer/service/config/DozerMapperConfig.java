package com.cy.store.commom.copyUtils.dozer.service.config;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date ：Created in 2019/10/9 10:40
 * @description：Dozer转换
 * @modified By：
 * @version: 1.0
 */
@Configuration
public class DozerMapperConfig {

    @Bean
    public Mapper dozerBeanMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}
