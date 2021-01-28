package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.Notice;
import cn.zcbigdata.mybits_demo.service.INoticeService;
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
@RequestMapping(value = "/notice")
public class NoticeController {

    private static final Logger logger = Logger.getLogger(NoticeController.class);

    @Resource
    private INoticeService noticeService;


    /**
     * 管理员和教师添加公告的controller层
     * 请求方式：GET
     * 入参：公告内容：content；标记：flag为session中的userType；用户id：userid为session中的userid
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/addNotice", method = RequestMethod.GET)
    @ResponseBody
    public String addNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 4)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        if (!UtilTools.checkNull(new String[]{content})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Notice notice = new Notice();
        notice.setContent(content.trim());
        notice.setFlag(Integer.valueOf((String) session.getAttribute("userType")));
        notice.setUserid(Integer.valueOf((String) session.getAttribute("userid")));
        noticeService.addNotice(notice);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 管理员和教师修改公告的controller层
     * 请求方式：GET
     * 入参：公告内容：content；公告id：id
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/updateNotice", method = RequestMethod.GET)
    @ResponseBody
    public String updateNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 4)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{content, idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Notice notice = new Notice();
        notice.setId(Integer.valueOf(idStr.trim()));
        notice.setContent(content.trim());
        noticeService.updateNotice(notice);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 管理员和教师删除公告的controller层
     * 请求方式：GET
     * 入参：公告id：id
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/deleteNotice", method = RequestMethod.GET)
    @ResponseBody
    public String deleteNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 4)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        noticeService.deleteNotice(Integer.parseInt(idStr.trim()));
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 查询管理员公告的controller层
     * 请求方式：GET
     * 入参：页码：page；数据量：limit
     * 出参：公告id：id；公告内容：content
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectAdminNotice", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectAdminNotice(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 4)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageString = request.getParameter("page");
        String limitString = request.getParameter("limit");
        if (!UtilTools.checkNull(new String[]{pageString, limitString})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Notice> notices = noticeService.selectAdminNotice(Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        String[] colums = {"id", "content"};
        return JsonUtil.listToLayJson(colums, notices);
    }

    /**
     * 查询教师公告的controller层
     * 请求方式：GET
     * 入参：页码：page；数据量：limit
     * 出参：公告id：id；公告内容：content
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTeacherNotice", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTeacherNotice(HttpServletRequest request) throws Exception {
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
        List<Notice> notices = noticeService.selectTeacherNotice(Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        String[] colums = {"id", "content"};
        return JsonUtil.listToLayJson(colums, notices);
    }

    /**
     * 查询管理员公告数量的controller层
     * 请求方式：GET
     * 入参：无
     * 出参：提示是否返回成功和数据总数量的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectAdminNoticeCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectAdminNoticeCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 4)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count = noticeService.selectAdminNoticeCount();
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }

    /**
     * 查询教师公告数量的controller层
     * 请求方式：GET
     * 入参：无
     * 出参：提示是否返回成功和数据总数量的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectTeacherNoticeCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectTeacherNoticeCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count = noticeService.selectTeacherNoticeCount();
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }


}
