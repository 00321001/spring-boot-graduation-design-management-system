package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Admin;
import cn.zcbigdata.mybits_demo.entity.Teacher;
import cn.zcbigdata.mybits_demo.service.IAdminService;
import cn.zcbigdata.mybits_demo.service.ITeacherService;
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
 * @author yty
 */
@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    private static final Logger logger = Logger.getLogger(TeacherController.class);
    @Resource
    private ITeacherService teacherService;

    /**
     * 教师登录接口Controller层实现，需要前台传回用户名：userName；密码：password
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    String teacherLogin(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        logger.info("userName：" + userName + "-----password：" + password);
        if (!UtilTools.checkNull(new String[]{userName, password})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Teacher teacher = new Teacher();
        teacher.setUserName(userName.trim());
        teacher.setPassword(password.trim());
        teacher = this.teacherService.teacherLogin(teacher);
        if (teacher == null) {
            return UtilTools.FAIL_RETURN_JSON;
        }
        HttpSession session = request.getSession();
        session.setAttribute("userid", teacher.getId().toString());
        session.setAttribute("userType", "1");
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师修改密码接口Controller层实现，需要前台传回旧密码：oldPassword；新密码：newPassword
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    String resetPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
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
        int flag = this.teacherService.resetPassword(map);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
