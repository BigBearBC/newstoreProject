package com.cy.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.store.base.Result;
import com.cy.store.commom.BaseContext;
import com.cy.store.config.Exception.CustomException;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/reg")
    public Result registerUser(User user) {
        userService.registerUser(user);
        return Result.ok("注册成功");
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result loginUser(User user, HttpServletRequest request) {
        User loginUser = userService.login(user, request);
        request.getSession().setAttribute("user", loginUser);
        BaseContext.setThreadLocal(loginUser);
        System.out.println(BaseContext.getThreadLocal());
        return Result.ok(loginUser, "登录成功");
    }

    /**
     * 用户修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param request
     * @return
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(String oldPassword, String newPassword, HttpServletRequest request) {
        if (oldPassword.equals(newPassword)) {
            throw new CustomException("原密码和新密码不能相同，请重新输入！");
        }
        userService.updatePassword(oldPassword, newPassword, request);
        return Result.ok("修改成功");
    }

    /**
     * 修改个人资料
     *
     * @param user
     * @param request
     * @return
     */
    @PutMapping("/updatePersonalData")
    public Result updatePersonalData(User user, HttpServletRequest request) {
        userService.updatePersonalData(user, request);
        return Result.ok("修改成功");
    }

    /**
     * 获取当前登陆用户的信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getPersonInfoByUsername")
    public Result getPersonInfoByUsername(HttpServletRequest request) {
        User loginedUser = (User) request.getSession().getAttribute("user");
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginedUser.getUsername()));
        return Result.ok(user);
    }


    /**
     * 上传文件
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/updatePersonAvatar")
    public Result updatePersonAvatar(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = userService.updatePersonAvatar(file, request);
        return Result.ok(map);
    }


}
