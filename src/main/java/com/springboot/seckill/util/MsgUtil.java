package com.springboot.seckill.util;


import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MsgUtil {
    private Object info;
    private JSONObject value;
    private boolean is;

}
