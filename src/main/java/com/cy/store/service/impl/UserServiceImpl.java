package com.cy.store.service.impl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.config.Exception.CustomException;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param user
     */
    @Transactional
    public void registerUser(User user) {
        //判断用户名是否已经存在，存在抛出异常
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername());
        if (userMapper.selectOne(queryWrapper) != null) {
            throw new CustomException("用户名已存在，请重试！！！");
        }

        //生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);

        //加密密码（使用MD5加密三次）
        String md5Password = getMd5Password(salt, user.getPassword());
        user.setPassword(md5Password);
        user.setCreatedUser(user.getUsername());
        //保存用户
        userMapper.insert(user);

    }

    /**
     * 将密码进行三次MD5加密并且都为大写
     *
     * @param salt
     * @param password
     * @return
     */
    @Transactional
    public String getMd5Password(String salt, String password) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Transactional
    public User login(User user, HttpServletRequest request) {
        //查询是否存在数据库中的数据
        User loginUser = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, user.getUsername())
                        .eq(User::getIsDelete, 0));
        if (ObjectUtils.isNull(loginUser)) {
            throw new CustomException("您输入的用户名不存在");
        }
        //将获取到的密码进行加密再和数据库中的密码进行比较
        String md5Password = getMd5Password(loginUser.getSalt(), user.getPassword());
        if (!md5Password.equals(loginUser.getPassword())) {
            throw new CustomException("您输入的密码错误");
        }
        return loginUser;
    }

    /**
     * 用户修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param request
     */
    @Transactional
    public void updatePassword(String oldPassword, String newPassword, HttpServletRequest request) {
        //获取当前登陆人的信息
        User user = (User) request.getSession().getAttribute("user");
        //查找当前登陆人的信息
        User loginedUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (ObjectUtils.isNull(loginedUser)) {
            throw new CustomException("未获取到当前登录人的信息，请联系管理员！");
        }
        //判断原密码是否正确
        String oldMd5Password = getMd5Password(loginedUser.getSalt(), oldPassword);
        if (!oldMd5Password.equals(loginedUser.getPassword())) {
            throw new CustomException("原密码错误，请确认密码");
        }
        //修改密码
        String newMd5Password = getMd5Password(loginedUser.getSalt(), newPassword);
        update(new LambdaUpdateWrapper<User>().eq(User::getUsername, loginedUser.getUsername())
                .set(User::getPassword, newMd5Password)
                .set(User::getModifiedTime, LocalDateTime.now())
                .set(User::getModifiedUser, user.getUsername())
        );
    }

    /**
     * 修改个人资料
     *
     * @param user
     * @param request
     */
    @Transactional
    public void updatePersonalData(User user, HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("user");
        User gotUser = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginUser.getUsername()));
        if (user.getPhone().equals(gotUser.getPhone())) {
            throw new CustomException("手机号不能和原手机号相同");
        }
        //验证手机号码格式是否正确
        if (!ReUtil.isMatch(PatternPool.MOBILE, Optional.ofNullable(user.getPhone()).orElse(""))) {
            throw new CustomException("请保证手机号码格式正确");
        }
        if (user.getEmail().equals(gotUser.getEmail())) {
            throw new CustomException("电子邮箱不能和原电子邮箱相同");
        }
        //验证邮箱格式是否正确
        if (!ReUtil.isMatch(PatternPool.EMAIL, Optional.ofNullable(user.getEmail()).orElse(""))) {
            throw new CustomException("请保证邮箱格式正确");
        }
        update(new LambdaUpdateWrapper<User>()
                .eq(User::getUsername, loginUser.getUsername())
                .set(User::getPhone, user.getPhone())
                .set(User::getEmail, user.getEmail())
                .set(User::getGender, user.getGender())
        );
    }

    //定义文件类型
    private static final List<String> ALLOW_FILE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/bmp", "image/gif");

    //定义文件大小
    private static final Integer ALLOW_FILE_SIZE = 10 * 1024 * 1024;


    @Transactional
    public Map<String, Object> updatePersonAvatar(MultipartFile file, HttpServletRequest request) {
        //判断文件类型是否满足要求
        if (!ALLOW_FILE_TYPES.contains(file.getContentType())) {
            throw new CustomException("您上传的文件不支持该格式，应该要jpeg，png，bmp，gif格式");
        }
        //判断文件大小是否满足要求
        if (file.getSize() > ALLOW_FILE_SIZE) {
            throw new CustomException("文件太大，请重新上传");
        }
        //获取保存路径的地址
        String parent = request.getSession().getServletContext().getRealPath("/upload/");
        System.out.println("parent" + parent);
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //获取上传文件的名称
        String fileName = IdUtil.fastSimpleUUID() +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File dest = new File(parent + fileName);

        //将文件写入目标文件
        boolean success = false;
        try {
            file.transferTo(dest);
            success = true;
        } catch (IOException e) {
            throw new CustomException("文件上传失败，请重新上传");
        }

        //保存当前登陆人的头像
        User loginedUser = (User) request.getSession().getAttribute("user");
        update(new LambdaUpdateWrapper<User>()
                .set(User::getAvatar, "/upload/" + fileName)
                .eq(User::getUsername, loginedUser.getUsername()));
        //创建返回值
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("filePath", "/upload/" + fileName);
        map.put("fileName", fileName);
        map.put("success", success);
        return map;
    }
}
