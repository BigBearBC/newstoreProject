package com.cy.store.commom;

/**
 *  基于ThreadLocal封装工具类，用于保存和获取当前登录用户的ID
 */
public class BaseContext {
    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    /**
     * 获取值
     * @return
     */
    public static Object getThreadLocal() {
        return threadLocal.get();
    }

    /**
     * 设置值
     * @param user
     */
    public static void setThreadLocal(Object user) {
        threadLocal.set(user);
    }
}
