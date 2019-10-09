package com.springboot.seckill.controller;


import lombok.extern.slf4j.Slf4j;
import com.springboot.seckill.entity.User;
import com.springboot.seckill.redis.RedisService;
import com.springboot.seckill.service.UserService;
import com.springboot.seckill.util.MD5Util;
import com.springboot.seckill.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenUtil tokenUtil;

    @RequestMapping("/post/login")
    public String login(Model model,
                        @RequestParam(value="phone", required=false) String phone,
                        @RequestParam(value="pwd",required=false) String password) {
        model.addAttribute("msg", null);
        if(password!=null&&phone!=null&&!password.equals(MD5Util.empty)){
            User user = userService.getUserByPhone(phone);

            if(user==null)  model.addAttribute("msg", "请检查手机号");
            else if(checkLogin(user.getSalt(), user.getPassword(), password)){

                String token = tokenUtil.createToken(phone);
                redisService.addToken(phone, token);
                log.info("success login: ------->" + token);

                return "redirect:/get/seckillVo/list/" + token;
            }else model.addAttribute("msg", "错误的密码");
        }

        return "login";
    }

    private boolean checkLogin(String salt, String DBPassword, String password) {
        if(DBPassword == null || DBPassword.equals("")) return false;
        MD5Util md5Util = new MD5Util(salt);
        String final_pwd = md5Util.md5(password);
        return final_pwd.equals(DBPassword);
    }

}
