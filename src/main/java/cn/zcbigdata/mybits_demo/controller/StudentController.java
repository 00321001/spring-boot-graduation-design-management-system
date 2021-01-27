package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Student;
import cn.zcbigdata.mybits_demo.service.IStudentService;
import cn.zcbigdata.mybits_demo.utils.JsonUtil;
import cn.zcbigdata.mybits_demo.utils.UtilTools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {

    private static final Logger logger = Logger.getLogger(TeacherController.class);
    @Resource
    private IStudentService studentService;

    @Value("${define.nginx.path}")
    private String nginxPath;

    /**
     * 根据教师id查询学生信息，需要前台传入当前页码page和每页数据条数limit
     * @param request HttpServletRequest
     * @return layui风格的json
     */
    @RequestMapping(value = "/selectStudentByTeacherid", method = RequestMethod.GET)
    @ResponseBody
    public String selectStudentByTeacherid(HttpServletRequest request) {
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

    /**
     * 教师添加单个学生的方法
     * @param request HttpServletRequest
     * @return 包含响应码和提示信息的json
     */
    @RequestMapping(value = "/teacherAddStudent", method = RequestMethod.POST)
    @ResponseBody
    public String teacherAddStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(!UtilTools.checkLogin(session,1)){
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String userName = request.getParameter("userName");
        String nickName = request.getParameter("nickName");
        if(!UtilTools.checkNull(new String[]{userName, userName})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Student student = new Student();
        student.setUserName(userName);
        student.setNickName(nickName);
        student.setTeacherid(Integer.valueOf((String) session.getAttribute("userid")));
        int flag = this.studentService.teacherAddStudent(student);
        if(flag == 1){
            return UtilTools.SUCCESS_RETURN_JSON;
        }else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    @RequestMapping(value = "/teacherAddStudentsUseXml", method = RequestMethod.POST)
    @ResponseBody
    public String teacherAddStudentsUseXml(@RequestParam("file") MultipartFile file, HttpSession session){
        //登录检查
        if(!UtilTools.checkLogin(session,1)){
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        //检查文件和文件名是否为空
        String fileName = file.getOriginalFilename();
        if(file.isEmpty() || fileName == null || "".equals(fileName)){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        logger.info("上传的文件是：" + fileName);
        //判断后缀是否为xml格式
        int pointIndex = fileName.lastIndexOf('.');
        if(pointIndex < 0 || !"xml".equals(fileName.substring(pointIndex + 1))){
            logger.info("后缀不为xml");
            return "{\"code\":\"9999\",\"msg\":\"请上传后缀为xml格式的文件\"}";
        }
        try{
            //将相关参数传到Service层
            Map<String, String> map = this.studentService.teacherAddStudentsUseXml(Integer.valueOf((String) session.getAttribute("userid")),file.getBytes(),nginxPath);
            logger.info(map.get("msg"));
            return "{\"code\":\"" + map.get("code") + "\",\"msg\":\"" + map.get("msg") + "\"}";
        }catch (Exception e){
            logger.error("将文件转换为二进制流出现问题");
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testPage(){return "test";}

}
