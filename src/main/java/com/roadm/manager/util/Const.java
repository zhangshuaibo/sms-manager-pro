package com.roadm.manager.util;

import org.springframework.context.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhangshuaibo
 * Date: 2019-06-10
 * Time: 09:45
 */
public class Const {
    public static final String LOGIN_PASS = "1";
    public static final String LOGIN_FAIL = "2";
    public static final int USER_STATE_NORMAL = 1;
    /**
     * 该值会在web容器启动时由WebAppContextListener初始化
     */
    public static ApplicationContext WEB_APP_CONTEXT = null;
}
