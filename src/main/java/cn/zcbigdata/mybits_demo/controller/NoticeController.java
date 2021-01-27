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

    @RequestMapping(value = "/adminAddNotice", method = RequestMethod.GET)
    @ResponseBody
    String adminAddNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        if (!UtilTools.checkNull(new String[]{content})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Notice notice = new Notice();
        notice.setContent(content);
        notice.setFlag(0);
        notice.setUserid(Integer.valueOf((String) session.getAttribute("userid")));
        noticeService.adminAddNotice(notice);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    @RequestMapping(value = "/adminUpdateNotice", method = RequestMethod.GET)
    @ResponseBody
    String adminUpdateNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        String idstr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{content,idstr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Notice notice = new Notice();
        notice.setId(Integer.valueOf(idstr));
        notice.setContent(content);
        noticeService.adminUpdateNotice(notice);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    @RequestMapping(value = "/adminDeleteNotice", method = RequestMethod.GET)
    @ResponseBody
    String adminDeleteNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idstr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idstr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        noticeService.adminDeleteNotice(Integer.valueOf(idstr));
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    @ResponseBody
    @RequestMapping(value = "/selectAdminNotice", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectAdminNotice(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String pageString = request.getParameter("page");
        String limitString = request.getParameter("limit");
        if (!UtilTools.checkNull(new String[]{pageString,limitString})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<Notice> notices = noticeService.selectAdminNotice(Integer.valueOf(pageString), Integer.valueOf(limitString));
        String[] colums = {"id", "content"};
        return JsonUtil.listToLayJson(colums, notices);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAdminNoticeCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectAdminNoticeCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 0)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count = noticeService.selectAdminNoticeCount();
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }


}
