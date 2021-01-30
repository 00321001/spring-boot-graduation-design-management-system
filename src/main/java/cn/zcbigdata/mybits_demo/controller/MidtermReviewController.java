package cn.zcbigdata.mybits_demo.controller;

import cn.zcbigdata.mybits_demo.entity.MidtermReview;
import cn.zcbigdata.mybits_demo.service.IMidtermReviewService;
import cn.zcbigdata.mybits_demo.utils.JsonUtil;
import cn.zcbigdata.mybits_demo.utils.UtilTools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/midterm")
public class MidtermReviewController {
    private static final Logger logger = Logger.getLogger(MidtermReviewController.class);

    @Resource
    private IMidtermReviewService midtermReviewService;

    /**
     * 通过教师id查询中期论文的controller层
     * 请求方式：GET
     * 入参：页码：page；数据量：limit；教师id：teacherid从session中的userid中获取
     * 出参：中期论文ID：id；中期论文：content；评语：comments；学生id：studentid；教师id：teacherid；标记：flag
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectMidtermByTeacherId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectMidtermByTeacherId(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String flagStr = request.getParameter("flag");
        String pageString = request.getParameter("page");
        String limitString = request.getParameter("limit");
        if (!UtilTools.checkNull(new String[]{flagStr,pageString, limitString})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        List<MidtermReview> midtermReviews;
        if(Integer.parseInt(flagStr.trim()) == 0){
            midtermReviews = midtermReviewService.selectMidtermByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),0,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        }else if(Integer.parseInt(flagStr.trim()) == 1){
            midtermReviews = midtermReviewService.selectMidtermByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),1,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        }else if(Integer.parseInt(flagStr.trim()) == 2){
            midtermReviews = midtermReviewService.selectMidtermByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),2,Integer.parseInt(pageString.trim()), Integer.parseInt(limitString.trim()));
        }else{
            return UtilTools.FAIL_RETURN_JSON;
        }
        String[] colums = {"id", "content", "comments", "studentid", "teacherid", "flag"};
        return JsonUtil.listToLayJson(colums, midtermReviews);
    }

    /**
     * 通过教师id查询中期论文数量的controller层
     * 请求方式：GET
     * 入参：教师id：teacherid为session中的userid
     * 出参：提示是否成功和数据数量的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectMidtermCountByTeacherId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectMidtermCountByTeacherId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count;
        String flagStr = request.getParameter("flag");
        if (!UtilTools.checkNull(new String[]{flagStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        if(Integer.parseInt(flagStr.trim()) == 0){
            count = midtermReviewService.selectMidtermCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),0);
        }else if(Integer.parseInt(flagStr.trim()) == 1){
            count = midtermReviewService.selectMidtermCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),1);
        }else if(Integer.parseInt(flagStr.trim()) == 2){
            count = midtermReviewService.selectMidtermCountByTeacherId(Integer.parseInt((String) session.getAttribute("userid")),2);
        }else{
            return UtilTools.FAIL_RETURN_JSON;
        }
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }

    /**
     * 通过学生id查询中期论文的controller层
     * 请求方式：GET
     * 入参：学生id：studentid从session中的userid中获取
     * 出参：中期论文ID：id；中期论文：content；评语：comments；学生id：studentid；教师id：teacherid；标记：flag
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectMidtermByStudentId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectMidtermByStudentId(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        List<MidtermReview> midtermReviews = midtermReviewService.selectMidtermByStudentId(Integer.parseInt((String) session.getAttribute("userid")));
        String[] colums = {"id", "content", "comments", "studentid", "teacherid", "flag"};
        return JsonUtil.listToLayJson(colums, midtermReviews);
    }

    /**
     * 通过学生id查询中期论文数量的controller层
     * 请求方式：GET
     * 入参：学生id：studentid为session中的userid
     * 出参：提示是否成功和数据数量的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectMidtermCountByStuId", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectMidtermCountByStuId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        int count = midtermReviewService.selectMidtermCountByStuId(Integer.parseInt((String) session.getAttribute("userid")));
        String data = String.valueOf(count);
        return "{\"code\":\"0000\",\"msg\":\"操作成功\",\"count\":\"" + data + "\"}";
    }

    /**
     * 教师通过中期论文的controller层
     * 请求方式：GET
     * 入参：中期论文id：id；标记：flag为1
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/checkMidtermYes", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String checkMidtermYes(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        MidtermReview midtermReview = new MidtermReview();
        midtermReview.setId(Integer.parseInt(idStr.trim()));
        midtermReview.setFlag(1);
        midtermReviewService.checkMidterm(midtermReview);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师不通过中期论文的controller层
     * 请求方式：GET
     * 入参：中期论文id：id；标记：flag为2
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/checkMidtermNo", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String checkMidtermNo(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        MidtermReview midtermReview = new MidtermReview();
        midtermReview.setId(Integer.parseInt(idStr.trim()));
        midtermReview.setFlag(2);
        midtermReviewService.checkMidterm(midtermReview);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 教师评语的controller层
     * 请求方式：GET
     * 入参：中期论文id：id；标记：flag为2；评语：comments
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/addComments", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String addComments(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        String comments = request.getParameter("comments");
        if (!UtilTools.checkNull(new String[]{idStr, comments})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        MidtermReview midtermReview = new MidtermReview();
        midtermReview.setId(Integer.parseInt(idStr.trim()));
        midtermReview.setComments(comments.trim());
        midtermReviewService.addComments(midtermReview);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 学生提交中期论文的controller层，如果已经提交了中期论文且处于待审核和通过状态则不可提交
     * 请求方式：GET
     * 入参：中期论文：content；学生id：studentid为session中的userid；教师id：teacherid；标记：flag为0
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @RequestMapping(value = "/addMidterm", method = RequestMethod.GET)
    @ResponseBody
    public String addMidterm(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String content = request.getParameter("content");
        String teacheridStr = request.getParameter("teacherid");
        if (!UtilTools.checkNull(new String[]{content, teacheridStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        if (midtermReviewService.selectMidtermCountByStudentId(Integer.parseInt((String) session.getAttribute("userid"))) > 0) {
            return UtilTools.FAIL_RETURN_JSON;
        }
        MidtermReview midtermReview = new MidtermReview();
        midtermReview.setContent(content.trim());
        midtermReview.setTeacherid(Integer.parseInt(teacheridStr.trim()));
        midtermReview.setStudentid(Integer.parseInt((String) session.getAttribute("userid")));
        midtermReview.setFlag(0);
        midtermReviewService.addMidterm(midtermReview);
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 通过中期论文id查询中期论文的controller层
     * 请求方式：GET
     * 入参：中期论文id：id
     * 出参：中期论文ID：id；中期论文：content；评语：comments；学生id：studentid；教师id：teacherid；标记：flag
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/selectMidtermById", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String selectMidtermById(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 3)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        List<MidtermReview> midtermReviews = midtermReviewService.selectMidtermById(Integer.parseInt(idStr.trim()));
        String[] colums = {"id", "content", "comments", "studentid", "teacherid", "flag"};
        return JsonUtil.listToLayJson(colums, midtermReviews);
    }

    /**
     * 删除中期论文的controller层
     * 请求方式：GET
     * 入参：中期论文id：id
     * 出参：提示是否成功的json
     *
     * @param request HttpServletRequest
     * @return 提示是否成功的json
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMidterm", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String deleteMidterm(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (!UtilTools.checkLogin(session, 1)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if (!UtilTools.checkNull(new String[]{idStr})) {
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        midtermReviewService.deleteMidterm(Integer.parseInt(idStr.trim()));
        return UtilTools.SUCCESS_RETURN_JSON;
    }

    /**
     * 学生下载中期论文接口，
     * 请求方式：POST，
     * 入参：开题报告id：id，
     * 出参：含有响应码和提示信息的json，
     * @param session HttpSession
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 含有响应码和提示信息的json
     */
    @PostMapping(value = "downloadMidterm")
    @ResponseBody
    public  String downloadFinalAudit(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        if (!UtilTools.checkLogin(session, 2)) {
            return UtilTools.NO_LOGIN_RETURN_JSON;
        }
        String idStr = request.getParameter("id");
        if(!UtilTools.checkNull(new String[]{idStr})){
            return UtilTools.IS_NULL_RETURN_JSON;
        }
        Map<String, String> map = this.midtermReviewService.downloadMidterm(response, Integer.valueOf(idStr.trim()));
        return "{\"code\":\"" + map.get("code") + "\",\"msg\":\"" + map.get("msg") + "\"}";
    }

}
