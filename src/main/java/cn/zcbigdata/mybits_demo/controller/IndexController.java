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
     * 跳转管理员端接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(HttpServletRequest request) {
        return "back1";
    }

    /**
     * 跳转教师端接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public String teacher(HttpServletRequest request) {
        return "back2";
    }

    /**
     * 跳转教师端接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String student(HttpServletRequest request) {
        return "back3";
    }

    /**
     * 跳转学生端个人信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoMyself", method = RequestMethod.GET)
    public String GoMyself(HttpServletRequest request) {
        return "myselfManage";
    }

    /**
     * 跳转学生端公告信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoStudentNotice", method = RequestMethod.GET)
    public String GoStudentNotice(HttpServletRequest request) {
        return "studentNotice";
    }

    /**
     * 跳转学生端可选毕设题目页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoChooseTitle", method = RequestMethod.GET)
    public String GoChooseTitle(HttpServletRequest request) {
        return "chooseTitle";
    }

    /**
     * 跳转学生端毕设题目管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoStudentTitle", method = RequestMethod.GET)
    public String GoStudentTitle(HttpServletRequest request) {
        return "studentTitle";
    }

    /**
     * 跳转学生端中期论文管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoStudentMidterm", method = RequestMethod.GET)
    public String GoStudentMidterm(HttpServletRequest request) {
        return "studentMidterm";
    }

    /**
     * 跳转管理员教师信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */

    @RequestMapping(value = "/GoStudentFinal", method = RequestMethod.GET)
    public String GoStudentFinal(HttpServletRequest request) {
        return "studentFinal";
    }

    /**
     * 跳转管理员教师信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */

    @RequestMapping(value = "/GoStudentOpening", method = RequestMethod.GET)
    public String GoStudentOpening(HttpServletRequest request) {
        return "studentOpeningReport";
    }

    /**
     * 跳转管理员教师信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoTeacher", method = RequestMethod.GET)
    public String GoTeacher(HttpServletRequest request) {
        return "teacherManage";
    }

    /**
     * 跳转管理员公告信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoAdminNotice", method = RequestMethod.GET)
    public String GoAdminNotice(HttpServletRequest request) {
        return "adminNotice";
    }

    /**
     * 跳转教师公告信息管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoTeacherNotice", method = RequestMethod.GET)
    public String GoTeacherNotice(HttpServletRequest request) {
        return "teacherNotice";
    }

    /**
     * 跳转教师毕设题目管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoTeacherTitle", method = RequestMethod.GET)
    public String GoTeacherTitle(HttpServletRequest request) {
        String flagStr = request.getParameter("flag");
        if (Integer.parseInt(flagStr) == 0) {
            return "teacherTitle0";
        } else if (Integer.parseInt(flagStr) == 1) {
            return "teacherTitle1";
        } else if (Integer.parseInt(flagStr) == 2) {
            return "teacherTitle2";
        } else if (Integer.parseInt(flagStr) == 3) {
            return "teacherTitle3";
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 跳转教师中期论文管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoTeacherMidterm", method = RequestMethod.GET)
    public String GoTeacherMidterm(HttpServletRequest request) {
        String flagStr = request.getParameter("flag");
        if (Integer.parseInt(flagStr) == 0) {
            return "teacherMidterm0";
        } else if (Integer.parseInt(flagStr) == 1) {
            return "teacherMidterm1";
        } else if (Integer.parseInt(flagStr) == 2) {
            return "teacherMidterm2";
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 跳转教师开题报告管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoTeacherOpening", method = RequestMethod.GET)
    public String GoTeacherOpening(HttpServletRequest request) {
        String flagStr = request.getParameter("flag");
        if (Integer.parseInt(flagStr) == 0) {
            return "teacherOpening0";
        } else if (Integer.parseInt(flagStr) == 1) {
            return "teacherOpening1";
        } else if (Integer.parseInt(flagStr) == 2) {
            return "teacherOpening2";
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 跳转教师论文终稿管理页面接口，无需入参
     *
     * @param request HttpServletRequest
     * @return 返回状态码和提示信息
     */
    @RequestMapping(value = "/GoTeacherFinal", method = RequestMethod.GET)
    public String GoTeacherFinal(HttpServletRequest request) {
        String flagStr = request.getParameter("flag");
        if (Integer.parseInt(flagStr) == 0) {
            return "teacherFinal0";
        } else if (Integer.parseInt(flagStr) == 1) {
            return "teacherFinal1";
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 跳转教师端学生管理页面接口
     *
     * @return 教师端学生管理页面
     */
    @RequestMapping(value = "/GoStudent", method = RequestMethod.GET)
    public String GoStudent() {
        return "studentManage";
    }

}
