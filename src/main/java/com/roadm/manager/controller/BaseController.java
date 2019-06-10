package com.roadm.manager.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本的控制器
 *
 * @author Mason
 */
public class BaseController {


    /**
     * @param request
     * @return
     */
    protected Map<String, Object> bindParamToMap(HttpServletRequest request) {
        Enumeration enumer = request.getParameterNames();
        Map<String, Object> map = new HashMap<String, Object>();
        while (enumer.hasMoreElements()) {
            String key = (String) enumer.nextElement();
            map.put(key, request.getParameter(key));
        }
        return map;
    }

    /**
     * 缁戝畾鍙傛暟鍒皉equest Attrbute
     *
     * @param request
     */
    protected void bindParamToAttrbute(HttpServletRequest request) {
        Enumeration enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String key = (String) enumer.nextElement();
            request.setAttribute(key, request.getParameter(key));
        }
    }


    /**
     * 获取客户端的Ip
     *
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request) {
        if (request.getHeader("X-Forwarded-For") == null
                || "".equals(request.getHeader("X-Forwarded-For"))) {
            return request.getRemoteAddr();
        } else {
            return request.getHeader("X-Forwarded-For");
        }
    }

    public void resposeStringWriteOut(String json, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.getWriter().write(json);
    }

    public void resposeJsonWriteOut(String json, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 添加cookie
     *
     * @param response
     * @param cName
     * @param cValue
     * @throws Exception
     */
    public void addACookie(HttpServletResponse response, String cName, String cValue) throws Exception {
        try {
            cValue = URLEncoder.encode(cValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        Cookie cookie = new Cookie(cName, cValue);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    /**
     * 记录系统日志
     *
     * @param map
     */
    protected void insertOperalog(Map map) {

    }
}
