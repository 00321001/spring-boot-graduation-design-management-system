package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.FinalAudit;
import cn.zcbigdata.mybits_demo.entity.FinalAudit;
import cn.zcbigdata.mybits_demo.service.IFinalAuditService;
import cn.zcbigdata.mybits_demo.utils.JsonUtil;
import cn.zcbigdata.mybits_demo.utils.UtilTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 论文终稿相关接口
 *
 * @author yty
 */
@Controller
@RequestMapping(value = "/finalAudit")
public class FinalAuditController {
    private static final String[] FIELDS = {"id", "content", "comments", "studentid", "teacherid", "flag"};
    @Resource
    private IFinalAuditService finalAuditService;

    /**
     * 根据教师id论文终稿接口，
     * 请求方式：GET，
     * 入参：报告状态：flag,
     * 出参：layui风格的json
     *
     * @param session HttpSession
     * @param request HttpServletRequest
     * @return layui风格的json
     */
    @RequestMapping(value = "selectFinalAuditByTeacherid", method = RequestMethod.GET)
    @ResponseBody
    public String selectFinalAuditByTeacherid(HttpServletRequest request,HttpSession session) throws Exception {
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String flagStr = request.getParameter("flag");
        String limitStr = request.getParameter("limit");
        String pageStr = request.getParameter("page");
        if(!UtilTools.checkNull(new String[]{flagStr, limitStr, pageStr})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Integer teacherid = Integer.valueOf((String) session.getAttribute("userid"));
        Integer flag = Integer.valueOf(flagStr.trim());
        int count = this.finalAuditService.selectCountByTeacherIdAndFlag(teacherid, flag);
        List<FinalAudit> finalAudits = this.finalAuditService.selectFinalAuditByTeacherid(teacherid, flag, Integer.valueOf(pageStr.trim()), Integer.valueOf(limitStr.trim()));
        return JsonUtil.listToNewLayJson(FIELDS, finalAudits ,count);
    }

    /**
     * 根据学生id查询论文终稿接口，
     * 请求方式：GET，
     * 入参：无入参，
     * 出参：layui风格的json
     *
     * @param session HttpSession
     * @return layui风格的json
     */
    @RequestMapping(value = "selectFinalAuditByStudentId", method = RequestMethod.GET)
    @ResponseBody
    public String selectFinalAuditByStudentId(HttpSession session) throws Exception {
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        List<FinalAudit> finalAudits = this.finalAuditService.selectFinalAuditByStudent(Integer.valueOf((String) session.getAttribute("userid")));
        return JsonUtil.listToLayJson(FIELDS, finalAudits);
    }

    /**
     * 根据论文终稿id查询论文终稿接口，
     * 请求方式：GET，
     * 入参：论文终稿id：id，
     * 出参：带有响应码和查询内容的json
     *
     * @param session HttpSession
     * @return 带有响应码和查询内容的json
     */
    @RequestMapping(value = "selectFinalAuditById", method = RequestMethod.GET)
    @ResponseBody
    public String selectFinalAuditById(HttpSession session, HttpServletRequest request) throws Exception {
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        FinalAudit finalAudit = this.finalAuditService.selectFinalAuditById(Integer.valueOf(idStr.trim()));
        return JsonUtil.objectToJson(FIELDS, finalAudit);
    }

    /**
     * 论文终稿审核接口
     * 请求方式：POST，
     * 入参：论文终稿id：id；教师评语：comments；审核标记：flag，
     * 出参：包含状态码和提示信息的json
     *
     * @param session HttpSession
     * @param request HttpServletRequest
     * @return 包含状态码和提示信息的json
     */
    @RequestMapping(value = "reviewFinalAudit", method = RequestMethod.POST)
    @ResponseBody
    public String reviewFinalAudit(HttpSession session, HttpServletRequest request) {
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String comments = request.getParameter("comments");
        String flagStr = request.getParameter("flag");
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{comments, flagStr, idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        FinalAudit finalAudit = new FinalAudit();
        finalAudit.setId(Integer.valueOf(idStr.trim()));
        finalAudit.setComments(comments.trim());
        finalAudit.setTeacherid(Integer.valueOf((String) session.getAttribute("userid")));
        finalAudit.setFlag(Integer.valueOf(flagStr.trim()));
        int flag = this.finalAuditService.reviewFinalAudit(finalAudit);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 提交论文终稿接口
     * 请求方式：POST，
     * 入参：论文终稿内容：content；教师id：teacherid
     * 出参：包含状态码和提示信息的json
     *
     * @param session HttpSession
     * @param request HttpServletRequest
     * @return 包含状态码和提示信息的json
     */
    @RequestMapping(value = "addFinalAudit", method = RequestMethod.POST)
    @ResponseBody
    public String addFinalAudit(HttpSession session, HttpServletRequest request) {
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        String teacherid = request.getParameter("teacherid");
        if (!UtilTools.checkNull(new String[]{content, teacherid})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        FinalAudit finalAudit = new FinalAudit();
        finalAudit.setTeacherid(Integer.valueOf(teacherid.trim()));
        finalAudit.setStudentid(Integer.valueOf((String) session.getAttribute("userid")));
        finalAudit.setContent(content.trim());
        int flag = this.finalAuditService.addFinalAudit(finalAudit);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
