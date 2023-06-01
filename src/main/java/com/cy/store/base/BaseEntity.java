package com.cy.store.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类的基类
 */
@Data
public class BaseEntity implements Serializable {

   @TableField(fill = FieldFill.INSERT)
   private String createdUser ;

   @TableField(fill = FieldFill.INSERT)
   private LocalDateTime createdTime ;

   @TableField(fill = FieldFill.INSERT_UPDATE)
   private String modifiedUser ;

   @TableField(fill = FieldFill.INSERT_UPDATE)
   private LocalDateTime modifiedTime ;
}
