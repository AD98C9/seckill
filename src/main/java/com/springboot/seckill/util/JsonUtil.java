package com.springboot.seckill.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
    public static MsgUtil stringToMsg(String json) {
        return JSON.parseObject(json, MsgUtil.class);
    }

    public static String classToString(Object object) {
        JSONObject jsonObject = new JSONObject();
        return JSON.toJSONString(object);
    }


}
