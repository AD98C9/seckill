package com.springboot.seckill.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MD5Util {
    public static String empty = "d41d8cd98f00b204e9800998ecf8427e";

    private String salt;

    public String md5(String str) {
        String base = str + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
