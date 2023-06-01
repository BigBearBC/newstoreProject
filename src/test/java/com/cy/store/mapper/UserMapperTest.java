package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
/*表示启动这个单元测试类（没有的话，单元测试类不能运行），
 需要传递一个参数必须是SpringRunner.class
 */
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试必须满足以下四个条件，不然不可以运行（满足条件后可以单独运行不需要启动整个项目）
     * 1.必须是被@Test注解修饰
     * 2.返回时必须是void
     * 3.方法的参数不能有
     * 4.方法的修饰符必须是public
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("HANHAN01");
        user.setPassword("123456");
        userMapper.insert(user);
    }


}
