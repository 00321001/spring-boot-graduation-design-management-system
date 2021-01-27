package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Student;
import cn.zcbigdata.mybits_demo.service.IStudentService;
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
import java.util.List;

/**
 * @author yty
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {

    private static final Logger logger = Logger.getLogger(TeacherController.class);
    @Resource
    private IStudentService studentService;

    /**
     * 根据教师id查询学生信息，需要前台传入当前页码page和每页数据条数limit
     * @param request HttpServletRequest
     * @return layui风格的json
     */
    @RequestMapping(value = "/selectStudentByTeacherid", method = RequestMethod.GET)
    @ResponseBody
    String selectStudentByTeacherid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(!UtilTools.checkLogin(session,1)){
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageStr = request.getParameter("page");
        String limitStr = request.getParameter("limit");
        if(!UtilTools.checkNull(new String[]{pageStr, limitStr})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Student> students = this.studentService.selectStudentByTeacherid(Integer.valueOf((String) session.getAttribute("userid")),Integer.valueOf(pageStr.trim()),Integer.valueOf(limitStr.trim()));
        try{
            return JsonUtil.listToLayJson(new String[]{"id", "userName", "password", "teacherid", "nickName"}, students);
        }catch (Exception e){
            logger.error(e.getStackTrace());
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
