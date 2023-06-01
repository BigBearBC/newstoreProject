package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cy.store.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_user")
public class User extends BaseEntity implements Serializable {

    private Integer uid ;

    private String username ;

    private String password ;

    private String salt;

    private String phone ;

    private String email ;

    private Integer gender ;

    private String avatar ;

    private Integer isDelete ;
}
