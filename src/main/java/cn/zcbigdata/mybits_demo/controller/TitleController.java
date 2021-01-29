package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Title;
import cn.zcbigdata.mybits_demo.service.ITitleService;
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

@Controller
@RequestMapping(value = "/title")
public class TitleController {

    private static final Logger logger = Logger.getLogger(TitleController.class);

    @Resource
    private ITitleService titleService;

    /**
     * 教师添加毕设题目的controller层，
     * 请求方式：GET
     * 入参：毕设题目：title；flag设置为0；teacherid从session中获取
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/addTitle", method = RequestMethod.GET)
    @ResponseBody
    public String addTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String title = request.getParameter("title");
        if (!UtilTools.checkNull(new String[]{title})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title titles = new Title();
        titles.setTitle(title.trim());
        titles.setFlag(0);
        titles.setTeacherid(Integer.valueOf((String) session.getAttribute("userid")));
        titleService.addTitle(titles);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师修改毕设题目的controller层
     * 请求方式：GET
     * 入参：毕设题目：title；该毕设题目的id：id
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/updateTitle", method = RequestMethod.GET)
    @ResponseBody
    public String updateTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String title = request.getParameter("title");
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{title, idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title titles = new Title();
        titles.setId(Integer.parseInt(idStr.trim()));
        titles.setTitle(title.trim());
        titleService.updateTitle(titles);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师删除毕设题目的controller层
     * 请求方式：GET
     * 入参：该毕设题目的id：id
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/deleteTitle", method = RequestMethod.GET)
    @ResponseBody
    public String deleteTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        titleService.deleteTitle(Integer.parseInt(idStr));
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 通过教师id查询毕设题目的controller层，教师查询全部题目，学生查询还没有被选的题目
     * 请求方式：GET
     * 需要前台传回的参数有两种情况：
     * ①如果该用户为教师，则只能看自己发布的毕设题目：
     * 入参：页码：page；数据量：limit；教师id：teacherid从session中的userid中获取
     * 出参：毕设题目ID：id；毕设题目：title；标记：flag；学生id：studentid
     * ②如果该用户为学生，则可以通过搜索自己老师的id，并且看到的毕设题目都是没有被选的（flag=0，studentid=0），其他的不会被获取：
     * 入参：页码：page；数据量：limit；教师id：teacherid
     * 出参：毕设题目ID：id；毕设题目：title；标记：flag（为0）；学生id：studentid（为空）
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTitleByTeacherId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTitleByTeacherId(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageString = request.getParameter("page");
        String limitString = request.getParameter("limit");
        if (!UtilTools.checkNull(new String[]{pageString, limitString})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Title> titles;
        if (Integer.parseInt((String) session.getAttribute("userType")) == 2) {
            String teacheridStr = request.getParameter("teacherid");
            if (!UtilTools.checkNull(new String[]{teacheridStr})) {
                return UtilTools.IS_NULL_RETURN_JSON;
            }
            titles = titleService.selectNotTitleByTeacherId(Integer.parseInt(teacheridStr.trim()), Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        } else {
            String flagStr = request.getParameter("flag");
            if (!UtilTools.checkNull(new String[]{flagStr})) {
                return UtilTools.IS_NULL_RETURN_JSON;
            }
            if(Integer.parseInt(flagStr.trim()) == 0){
                titles = titleService.selectTitleByTeacherId(Integer.parseInt((String) session.getAttribute("userid")), 0 ,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
            }else if(Integer.parseInt(flagStr.trim()) == 1){
                titles = titleService.selectTitleByTeacherId(Integer.parseInt((String) session.getAttribute("userid")), 1 ,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
            }else if(Integer.parseInt(flagStr.trim()) == 2){
                titles = titleService.selectTitleByTeacherId(Integer.parseInt((String) session.getAttribute("userid")), 2 ,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
            }else if(Integer.parseInt(flagStr.trim()) == 3){
                titles = titleService.selectTitleByTeacherId(Integer.parseInt((String) session.getAttribute("userid")), 3 ,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
            }else{
                return UtilTools.FAIL_RETURN_JSON;
            }
        }
        String[] colums = {"id", "title", "teacherid" ,"flag", "studentid"};
        return JsonUtil.listToLayJson(colums, titles);
    }

    /**
     * 通过教师id查询毕设题目数量的controller层，教师查询全部题目，学生查询还没有被选的题目
     * 请求方式：GET
     * 入参：①教师端：教师id：teacherid为session中的userid
     * ②学生端：教师id：teacherid
     * 出参：提示是否成功和数据数量的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTitleCountByTeacherId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTitleCountByTeacherId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count;
        if (Integer.parseInt((String) session.getAttribute("userType")) == 2) {
            String teacheridStr = request.getParameter("teacherid");
            if (!UtilTools.checkNull(new String[]{teacheridStr})) {
                return UtilTools.IS_NULL_RETURN_JSON;
            }
            count = titleService.selectNotTitleCountByTeacherId(Integer.parseInt(teacheridStr.trim()));
        } else {
            String flagStr = request.getParameter("flag");
            if (!UtilTools.checkNull(new String[]{flagStr})) {
                return UtilTools.IS_NULL_RETURN_JSON;
            }
            if(Integer.parseInt(flagStr.trim()) == 0){
                count = titleService.selectTitleCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),0);
            }else if(Integer.parseInt(flagStr.trim()) == 1){
                count = titleService.selectTitleCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),1);
            }else if(Integer.parseInt(flagStr.trim()) == 2){
                count = titleService.selectTitleCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),2);
            }else if(Integer.parseInt(flagStr.trim()) == 3){
                count = titleService.selectTitleCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),3);
            }else{
                return UtilTools.FAIL_RETURN_JSON;
            }
        }
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }

    /**
     * 通过学生id查询毕设题目的controller层
     * 请求方式：GET
     * 入参：学生id：student为session中的userid
     * 出参：毕设题目id：id；毕设题目：title；教师id：teacherid
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTitleByStudentId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTitleByStudentId(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        List<Title> titles = titleService.selectTitleByStudentId(Integer.parseInt((String) session.getAttribute("userid")));
        String[] colums = {"id", "title", "teacherid" ,"flag", "studentid"};
        return JsonUtil.listToLayJson(colums, titles);
    }

    /**
     * 学生选择毕设题目的controller层，如果已经选择了一个或提交了自拟题目则不可再选
     * 请求方式：GET
     * 入参：学生id：studentid为session中的userid；毕设题目id：id
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/chooseTitle", method = RequestMethod.GET)
    @ResponseBody
    public String chooseTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        if (titleService.selectTitleCountByStudentId(Integer.parseInt((String) session.getAttribute("userid"))) > 0) {
            return UtilTools.FAIL_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title title = new Title();
        title.setId(Integer.parseInt(idStr.trim()));
        title.setFlag(1);
        title.setStudentid(Integer.parseInt((String) session.getAttribute("userid")));
        titleService.chooseTitle(title);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 学生提交自拟毕设题目的controller层，如果已经选择了毕设题目或已经提交了一个自拟题目则不可提交
     * 请求方式：GET
     * 入参：自拟题目：title；学生id：studentid为session中的userid；教师id：teacherid
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/addStuTitle", method = RequestMethod.GET)
    @ResponseBody
    public String addStuTitle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        if (titleService.selectTitleCountByStudentId(Integer.parseInt((String) session.getAttribute("userid"))) > 0) {
            return UtilTools.FAIL_RETURN_JSON;
        }
        String teacheridStr = request.getParameter("teacherid");
        String stuTitle = request.getParameter("title");
        if (!UtilTools.checkNull(new String[]{teacheridStr, stuTitle})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title title = new Title();
        title.setTeacherid(Integer.parseInt(teacheridStr.trim()));
        title.setFlag(2);
        title.setStudentid(Integer.parseInt((String) session.getAttribute("userid")));
        title.setTitle(stuTitle.trim());
        titleService.addStuTitle(title);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 通过教师id查询自拟毕设题目的controller层
     * 请求方式：GET
     * 入参：页码：page；数据量：limit；教师id：teacherid从session中的userid中获取
     * 出参：毕设题目ID：id；毕设题目：title；标记：flag；学生id：studentid
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectStuTitle", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectStuTitle(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageString = request.getParameter("page");
        String limitString = request.getParameter("limit");
        if (!UtilTools.checkNull(new String[]{pageString, limitString})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Title> titles = titleService.selectStuTitle(Integer.parseInt((String) session.getAttribute("userid")), Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        String[] colums = {"id", "title", "flag", "studentid"};
        return JsonUtil.listToLayJson(colums, titles);
    }

    /**
     * 通过教师id查询毕设题目数量的controller层
     * 请求方式：GET
     * 入参：教师id：teacherid为session中的userid
     * 出参：提示是否成功和数据数量的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectStuTitleCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectStuTitleCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count = titleService.selectStuTitleCount(Integer.parseInt((String) session.getAttribute("userid")));
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }

    /**
     * 教师通过自拟毕设题目的controller层
     * 请求方式：GET
     * 入参：自拟题目id：id；标记：flag为1
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/checkStuTitleYes", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String checkStuTitleYes(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title title = new Title();
        title.setFlag(1);
        title.setId(Integer.parseInt(idStr.trim()));
        titleService.checkStuTitle(title);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师不通过自拟毕设题目的controller层
     * 请求方式：GET
     * 入参：自拟题目id：id；标记：flag为3
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/checkStuTitleNo", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String checkStuTitleNo(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title title = new Title();
        title.setFlag(3);
        title.setId(Integer.parseInt(idStr.trim()));
        titleService.checkStuTitle(title);
        return UtilTools.SUCCESS_RETURN_JSON;
    }
}
