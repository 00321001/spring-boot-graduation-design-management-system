package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.OpeningReport;
import cn.zcbigdata.mybits_demo.service.IOpeningReportService;
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
 * 开题报告相关接口
 *
 * @author yty
 */
@Controller
@RequestMapping(value = "/openingReport")
public class OpeningReportController {
    private static final String[] FIELDS = {"id", "content", "comments", "studentid", "teacherid", "flag"};
    @Resource
    private IOpeningReportService openingReportService;

    /**
     * 根据教师id查询开题报告接口，
     * 请求方式：GET，
     * 入参：无入参，
     * 出参：layui风格的json
     *
     * @param session HttpSession
     * @return layui风格的json
     */
    @RequestMapping(value = "selectOpeningReportByTeacherid", method = RequestMethod.GET)
    @ResponseBody
    public String selectOpeningReportByTeacherid(HttpSession session) throws Exception {
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        List<OpeningReport> openingReports = this.openingReportService.selectOpeningReportByTeacherid(Integer.valueOf((String) session.getAttribute("userid")));
        return JsonUtil.listToLayJson(FIELDS, openingReports);
    }

    /**
     * 根据学生id查询开题报告接口，
     * 请求方式：GET，
     * 入参：无入参，
     * 出参：layui风格的json
     *
     * @param session HttpSession
     * @return layui风格的json
     */
    @RequestMapping(value = "selectOpeningReportByStudentId", method = RequestMethod.GET)
    @ResponseBody
    public String selectOpeningReportByStudentId(HttpSession session) throws Exception {
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        List<OpeningReport> openingReports = this.openingReportService.selectOpeningReportByStudent(Integer.valueOf((String) session.getAttribute("userid")));
        return JsonUtil.listToLayJson(FIELDS, openingReports);
    }

    /**
     * 根据开题报告id查询开题报告接口，
     * 请求方式：GET，
     * 入参：开题报告id：id，
     * 出参：带有响应码和查询内容的json
     *
     * @param session HttpSession
     * @return 带有响应码和查询内容的json
     */
    @RequestMapping(value = "selectOpeningReportById", method = RequestMethod.GET)
    @ResponseBody
    public String selectOpeningReportById(HttpSession session, HttpServletRequest request) throws Exception {
        if (!UtilTools.checkLogin(session, 5)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        OpeningReport openingReport = this.openingReportService.selectOpeningReportById(Integer.valueOf(idStr.trim()));
        return JsonUtil.objectToJson(FIELDS, openingReport);
    }

    /**
     * 开题报告审核接口
     * 请求方式：POST，
     * 入参：开题报告id：id；教师评语：comments；审核标记：flag，
     * 出参：包含状态码和提示信息的json
     *
     * @param session HttpSession
     * @param request HttpServletRequest
     * @return 包含状态码和提示信息的json
     */
    @RequestMapping(value = "reviewOpeningReport", method = RequestMethod.POST)
    @ResponseBody
    public String reviewOpeningReport(HttpSession session, HttpServletRequest request) {
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String comments = request.getParameter("comments");
        String flagStr = request.getParameter("flag");
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{comments, flagStr, idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        OpeningReport openingReport = new OpeningReport();
        openingReport.setId(Integer.valueOf(idStr.trim()));
        openingReport.setComments(comments.trim());
        openingReport.setTeacherid(Integer.valueOf((String) session.getAttribute("userid")));
        openingReport.setFlag(Integer.valueOf(flagStr.trim()));
        int flag = this.openingReportService.reviewOpeningReport(openingReport);
        if (flag == 1) {
            return UtilTools.SUCCESS_RETURN_JSON;
        } else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }

    /**
     * 提交开题报告接口
     * 请求方式：POST，
     * 入参：报告内容：content；教师id：teacherid
     * 出参：包含状态码和提示信息的json
     *
     * @param session HttpSession
     * @param request HttpServletRequest
     * @return 包含状态码和提示信息的json
     */
    @RequestMapping(value = "addOpeningReport", method = RequestMethod.POST)
    @ResponseBody
    public String addOpeningReport(HttpSession session, HttpServletRequest request) {
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        String teacherid = request.getParameter("teacherid");
        if (!UtilTools.checkNull(new String[]{content, teacherid})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        OpeningReport openingReport = new OpeningReport();
        openingReport.setTeacherid(Integer.valueOf(teacherid.trim()));
        openingReport.setStudentid(Integer.valueOf((String) session.getAttribute("userid")));
        openingReport.setContent(content.trim());
        int flag = this.openingReportService.addOpeningReport(openingReport);
        if(flag == 1){
            return UtilTools.SUCCESS_RETURN_JSON;
        }else {
            return UtilTools.FAIL_RETURN_JSON;
        }
    }
}
