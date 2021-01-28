package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Admin;
import cn.zcbigdata.mybits_demo.service.IAdminService;
import cn.zcbigdata.mybits_demo.utils.JsonUtil;
import cn.zcbigdata.mybits_demo.utils.UtilTools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员表相关接口
 *
 * @author yty
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);
    @Resource
    private IAdminService adminService;

    /**
     * 管理员登录接口Controller层实现；
     * 请求方式：GET；
     * 入参：用户名：userName；密码：password；
     * 出参：提示是否成功的json；
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        logger.info("userName：" + userName + "-----password：" + password);
        if (!UtilTools.checkNull(new String[]{userName, password})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Admin admin = new Admin();
        admin.setUserName(userName.trim());
        admin.setPassword(password.trim());
        admin = this.adminService.adminLogin(admin);
        if (admin == null) {
            return UtilTools.FAIL_RETURN_JSON;
        }
        HttpSession session = request.getSession();
        session.setAttribute("userid", admin.getId().toString());
        session.setAttribute("userType", "0");
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 根据管理员id查询管理员信息接口
     * 请求方式：GET
     * 入参：无入参
     * 出参：包含响应码和管理员信息的JSON
     *
     * @param session HttpSession
     * @return 包含响应码和管理员信息的JSON
     */
    @RequestMapping(value = "/selectAdminById", method = RequestMethod.GET)
    @ResponseBody
    public String selectAdminById(HttpSession session) throws Exception {
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        Admin admin = this.adminService.selectAdminById(Integer.valueOf((String) session.getAttribute("userid")));
        return JsonUtil.objectToJson(new String[]{"id", "userName", "password", "nickName"}, admin);
    }

    /**
     * 检查管理员登录接口
     * 请求方式：GET
     * 入参：无入参
     * 出参：包含响应码和管理员id和用户名的JSON
     *
     * @param session HttpSession
     * @return 包含响应码和管理员id和用户名的JSON
     */
    @RequestMapping(value = "/loginCheck", method = RequestMethod.GET)
    @ResponseBody
    public String loginCheck(HttpSession session) throws Exception {
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        Admin admin = this.adminService.selectAdminById(Integer.valueOf((String) session.getAttribute("userid")));
        return JsonUtil.objectToJson(new String[]{"id", "userName"}, admin);
    }

    /**
     * 管理员修改密码接口Controller层实现；
     * 请求方式：POST；
     * 入参：旧密码：oldPassword；新密码：newPassword
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        logger.info("oldPassword：" + oldPassword + "-----newPassword：" + newPassword);
        if (!UtilTools.checkNull(new String[]{oldPassword, newPassword})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("oldPassword", oldPassword.trim());
        map.put("newPassword", newPassword.trim());
        map.put("id", (String) session.getAttribute("userid"));
        int flag = this.adminService.resetPassword(map);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
