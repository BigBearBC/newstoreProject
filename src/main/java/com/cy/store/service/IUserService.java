package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IUserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param user
     */
    void registerUser(User user);

    /**
     * 用户登录
     *
     * @param user
     * @param request
     * @return
     */
    User login(User user, HttpServletRequest request);

    /**
     * 用户修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param request
     */
    void updatePassword(String oldPassword, String newPassword, HttpServletRequest request);

    /**
     * 修改个人资料
     *
     * @param user
     * @param request
     */
    void updatePersonalData(User user, HttpServletRequest request);


    Map<String, Object> updatePersonAvatar(MultipartFile file, HttpServletRequest request);
}
