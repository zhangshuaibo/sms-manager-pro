package com.roadm.manager.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhangshuaibo
 * Date: 2019-06-10
 * Time: 15:48
 */
public class ResMessage {
    public static String withoutList(String status, String comment, Map<String, String> data) {

        if (data != null) {
            data.put("status", status);
            data.put("message", comment);
            return JSONObject.fromObject(data).toString();
        } else {
            Map<String, String> res = new HashMap<String, String>();
            res.put("status", status);
            res.put("message", comment);
            return JSONObject.fromObject(res).toString();
        }
    }

    public static String hasList(String status, String comment, List<Map<String, String>> data) {
        if (data != null && data.size() > 0) {
            Map<Object, Object> result = new HashMap<Object, Object>();

            result.put("dataList", data);
            result.put("totalCount", data.size());

            return JSONArray.fromObject(result).toString();

        } else {
            Map<String, String> res = new HashMap<String, String>();
            res.put("status", status);
            res.put("message", comment);
            return JSONObject.fromObject(res).toString();
        }
    }
}
