package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Admin;
import cn.zcbigdata.mybits_demo.service.IAdminService;
import cn.zcbigdata.mybits_demo.utils.UtilTools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yty
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);
    @Resource
    private IAdminService adminService;

    /**
     * 管理员登录接口Controller层实现
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    String adminLogin(HttpServletRequest request) {
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
}
