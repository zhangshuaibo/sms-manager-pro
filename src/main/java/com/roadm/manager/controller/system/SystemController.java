package com.roadm.manager.controller.system;

import com.roadm.manager.model.UserInfo;
import com.roadm.manager.service.SystemService;
import com.roadm.manager.service.UserService;
import com.roadm.manager.util.Const;
import com.roadm.manager.util.ResMessage;
import com.roadm.manager.util.Tools;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhangshuaibo
 * Date: 2019-06-10
 * Time: 11:06
 */
@RequestMapping("/system")
@Controller
public class SystemController {
    private Logger log = Logger.getLogger(SystemController.class);


    @Autowired
    private SystemService systemService;
    @Autowired
    private UserService userService;

    /**
     * 登录认证
     *
     * @param request
     * @param sessionId
     * @param token
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(HttpServletRequest request, @RequestParam(value = "sessionId", required = false) String sessionId,
                        @RequestParam(value = "token", required = false) String token,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "password", required = true) String password) {
        UserInfo user = null;
        String errInfo = "";
        String status = Const.LOGIN_FAIL;
        Map data = new HashMap<String, String>();
        user = userService.getUserByName(userName);
        if (user != null) {
            if (user.getStatus() != Const.USER_STATE_NORMAL) {
                errInfo = "用户名状态不正常！";
            } else if (!user.getPassword().equals(Tools.encodeByMD5(Tools.encodeByMD5(password)))) {
                errInfo = "您输入的密码不正确！";
            }else {
                status = Const.LOGIN_PASS;
                data.put("sessionId","");
                data.put("token","");
                data.put("userName","");
            }

        } else {
            errInfo = "用户名不存在！";
        }
        return ResMessage.withoutList(status, errInfo, data);
    }


    @RequestMapping(value = "/fetchUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public String fetchUserInfo(HttpServletRequest request, @RequestParam(value = "sessionId", required = true) String sessionId,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "userName", required = true) String userName,
                                @RequestParam(value = "password", required = true) String password) {

        List<Map<String, Object>> resultData = systemService.getData();
        Map<Object, Object> result = new HashMap<Object, Object>();

        result.put("dataList", resultData);
        result.put("totalCount", resultData.size());

        return JSONArray.fromObject(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/req/userInfo/{userName}", method = RequestMethod.GET)
    public String getUserInfo(@PathVariable("userName") String userName) {

        return "{\"error\":\"请求数据为空\"}";
    }


}
