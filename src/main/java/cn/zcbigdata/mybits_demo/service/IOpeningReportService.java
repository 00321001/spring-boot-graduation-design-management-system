package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.OpeningReport;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
public interface IOpeningReportService {

    /**
     * 根据教师id获取开题报告的Service层方法
     *
     * @param id    教师id
     * @param flag  报告状态
     * @param page  当前页码
     * @param limit 每页大小
     * @return 一个List，存有查询到的OpeningReport对象
     */
    List<OpeningReport> selectOpeningReportByTeacherid(Integer id, Integer flag, Integer page, Integer limit);

    /**
     * 根据学生id获取开题报告的Service层方法
     *
     * @param page  当前页码
     * @param limit 每页大小
     * @param id    学生id
     * @return 一个List，存有查询到的OpeningReport对象
     */
    List<OpeningReport> selectOpeningReportByStudent(Integer id, Integer page, Integer limit);

    /**
     * 教师审核开题报告并添加评语的Service层方法
     *
     * @param openingReport OpeningReport对象，存有报告id，评语，标记
     * @return 受影响的行数
     */
    Integer reviewOpeningReport(OpeningReport openingReport);

    /**
     * 根据开题报告id获取开题报告的Service层方法
     *
     * @param id 开题报告id
     * @return OpeningReport对象，存有相关信息
     */
    OpeningReport selectOpeningReportById(Integer id);

    /**
     * 添加开题报告的Service层方法
     *
     * @param openingReport OpeningReport对象，存有报告内容、学生id、教师id
     * @return 受影响行数
     */
    Integer addOpeningReport(OpeningReport openingReport);

    /**
     * 根据教师id和开题报告状态查询开题报告数量的Service层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag);

    /**
     * 下载开题报告的service层方法
     *
     * @param response HttpServletResponse
     * @param id       开题报告id
     * @return 存有状态码和提示信息的集合
     */
    Map<String, String> downloadOpenReport(HttpServletResponse response, Integer id);

    /**
     * 根据学生id获取开题报告总数的Service层方法
     *
     * @param id 学生id
     * @return 总数
     */
    Integer selectCountByStudentid(Integer id);
}
