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
     * 教师添加毕设题目的controller层
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
        titles.setTitle(title);
        titles.setFlag(0);
        titles.setTeacherid(Integer.valueOf((String) session.getAttribute("userid")));
        titleService.addTitle(titles);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师修改毕设题目的controller层
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
        if (!UtilTools.checkNull(new String[]{title,idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Title titles = new Title();
        titles.setId(Integer.valueOf(idStr));
        titles.setTitle(title);
        titleService.updateTitle(titles);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 通过教师id查询毕设题目的controller层
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTitleById", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTitleById(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageString = request.getParameter("page");
        String limitString = request.getParameter("limit");
        if (!UtilTools.checkNull(new String[]{pageString,limitString})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Title> titles;
        if(Integer.parseInt((String)session.getAttribute("userType")) == 2){
            String teacheridStr = request.getParameter("teacherid");
            if (!UtilTools.checkNull(new String[]{teacheridStr})) {
                return UtilTools.IS_NULL_RETURN_JSON;
            }
            titles = titleService.selectTitleById(Integer.parseInt(teacheridStr),Integer.parseInt(pageString), Integer.parseInt(limitString));
        }else{
            titles = titleService.selectTitleById(Integer.parseInt((String)session.getAttribute("userid")),Integer.parseInt(pageString), Integer.parseInt(limitString));
        }
        String[] colums = {"id", "title","flag","studentid"};
        return JsonUtil.listToLayJson(colums, titles);
    }

    /**
     * 通过教师id查询毕设题目数量的controller层
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTitleCountById", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTitleCountById(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count;
        if(Integer.parseInt((String)session.getAttribute("userType")) == 2){
            String teacheridStr = request.getParameter("teacherid");
            if (!UtilTools.checkNull(new String[]{teacheridStr})) {
                return UtilTools.IS_NULL_RETURN_JSON;
            }
            count = titleService.selectTitleCountById(Integer.parseInt(teacheridStr));
        }else{
            count = titleService.selectTitleCountById(Integer.parseInt((String) session.getAttribute("userid")));
        }
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }
}
