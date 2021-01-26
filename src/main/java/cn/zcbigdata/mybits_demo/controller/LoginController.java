package cn.zcbigdata.mybits_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yty
 */
@Controller
public class LoginController {

    /**
     * 返回登录页面的Controller层方法
     * @return 返回登录页面
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    String loginPage(){
        return "login";
    }
}
