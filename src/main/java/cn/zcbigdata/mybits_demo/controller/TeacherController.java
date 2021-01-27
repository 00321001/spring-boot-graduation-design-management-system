package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Admin;
import cn.zcbigdata.mybits_demo.entity.Teacher;
import cn.zcbigdata.mybits_demo.service.IAdminService;
import cn.zcbigdata.mybits_demo.service.ITeacherService;
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
import java.util.List;
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
        Map<String, String> map = new HashMap<>(3);
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

    /**
     * 管理员添加教师接口Controller层实现，需要前台传回用户名：userName；昵称：nickName
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/adminAddTeacher", method = RequestMethod.POST)
    @ResponseBody
    String adminAddTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String userName = request.getParameter("userName");
        String nickName = request.getParameter("nickName");
        logger.info("userName：" + userName + "-----nickName：" + nickName);
        if(!UtilTools.checkNull(new String[]{userName, nickName})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Teacher teacher = new Teacher();
        teacher.setUserName(userName.trim());
        teacher.setNickName(nickName.trim());
        int flag = this.teacherService.adminAddTeacher(teacher);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 管理员修改教师接口Controller层实现，需要前台传回教师id:teacherid；用户名：userName；密码：password；昵称：nickName
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/adminUpdateTeacher", method = RequestMethod.POST)
    @ResponseBody
    String adminUpdateTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String teacheridStr = request.getParameter("teacherid");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String nickName = request.getParameter("nickName");
        logger.info("teacherid：" + teacheridStr + "-----userName：" + userName + "-----password：" + password + "-----nickName：" + nickName);
        if(!UtilTools.checkNull(new String[]{userName, password, nickName, teacheridStr})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Teacher teacher = new Teacher();
        teacher.setId(Integer.valueOf(teacheridStr.trim()));
        teacher.setUserName(userName);
        teacher.setPassword(password);
        teacher.setNickName(nickName);
        int flag = this.teacherService.adminUpdateTeacher(teacher);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 管理员查询教师接口Controller层实现，需要前台传回当前页码：page；每页数量：limit
     *
     * @param request HttpServletRequest
     * @return laiui风格的Json
     */
    @RequestMapping(value = "/adminSelectTeacher", method = RequestMethod.GET)
    @ResponseBody
    String adminSelectTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageStr = request.getParameter("page");
        String limitStr = request.getParameter(("limit"));
        if(!UtilTools.checkNull(new String[]{pageStr, limitStr})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Teacher> teachers = this.teacherService.selectAll(Integer.valueOf(pageStr.trim()), Integer.valueOf(limitStr.trim()));
        try{
            return JsonUtil.listToLayJson(new String[]{"id", "userName", "password", "nickName"}, teachers);
        }catch (Exception e){
            logger.error(e.getStackTrace());
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 管理员查询教师总数接口Controller层实现，无需入参
     * @param request HttpServletRequest
     * @return 包含总数count的json
     */
    @RequestMapping(value = "/selectCount", method = RequestMethod.GET)
    @ResponseBody
    String selectCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        Integer count = this.teacherService.selectCount();
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"data\":{\"count\":\"" + count + "\"}}";
    }

    /**
     * 根据id查询教师信息接口Controller层实现，需要前台传入教师id：teacherid
     * @param request HttpServletRequest
     * @return 包含教师信息的json
     */
    @RequestMapping(value = "/selectTeacherById", method = RequestMethod.GET)
    @ResponseBody
    String selectTeacherById(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!(UtilTools.checkLogin(session, 4))) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String teacheridStr = request.getParameter("teacherid");
        if(!UtilTools.checkNull(new String[]{teacheridStr})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Teacher teacher = this.teacherService.selectTeacherById(Integer.valueOf(teacheridStr.trim()));
        try{
            return JsonUtil.objectToJson(new String[]{"id", "userName", "password", "nickName"}, teacher);
        }catch (Exception e){
            logger.error(e.getStackTrace());
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
