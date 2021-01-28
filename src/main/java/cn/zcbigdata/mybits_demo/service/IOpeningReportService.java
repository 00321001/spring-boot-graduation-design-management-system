package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.OpeningReport;

import java.util.List;

/**
 * @author yty
 */
public interface IOpeningReportService {

    /**
     * 根据教师id获取开题报告的Service层方法
     *
     * @param id 教师id
     * @return 一个List，存有查询到的OpeningReport对象
     */
    List<OpeningReport> selectOpeningReportByTeacherid(Integer id);

    /**
     * 根据学生id获取开题报告的Service层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的OpeningReport对象
     */
    List<OpeningReport> selectOpeningReportByStudent(Integer id);

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
}
