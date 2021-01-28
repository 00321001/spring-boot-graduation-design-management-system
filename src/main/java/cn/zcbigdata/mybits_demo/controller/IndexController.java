package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.utils.UtilTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 页面跳转相关接口
 *
 * @author ts119
 */
@Controller
public class IndexController {
    /**
     * 返回登录页面的Controller层方法
     *
     * @return 返回登录页面
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * 登出接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 返回批量添加学生测试页面的接口
     *
     * @return 返回批量添加学生测试页面
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testPage() {
        return "test";
    }
}
