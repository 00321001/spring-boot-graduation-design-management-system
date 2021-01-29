package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.OpeningReport;

import java.util.List;

/**
 * @author ts119
 */
public interface OpeningReportMapper {

    /**
     * 根据教师id获取开题报告的Mapper层方法
     *
     * @param id         教师id
     * @param flag       报告状态
     * @param startIndex 开始位置
     * @param pageSize   每页大小
     * @return 一个List，存有查询到的OpeningReport对象
     */
    List<OpeningReport> selectOpeningReportByTeacherid(Integer id, Integer flag, Integer startIndex, Integer pageSize);

    /**
     * 根据学生id获取开题报告的Mapper层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的OpeningReport对象
     */
    List<OpeningReport> selectOpeningReportByStudent(Integer id);

    /**
     * 教师审核开题报告并添加评语
     *
     * @param openingReport OpeningReport对象，存有报告id，评语，标记
     * @return 受影响的行数
     */
    Integer reviewOpeningReport(OpeningReport openingReport);

    /**
     * 根据开题报告id获取开题报告信息的Mapper层方法
     *
     * @param id 报告id
     * @return OpeningReport对象，存有相关信息
     */
    OpeningReport selectOpeningReportById(Integer id);

    /**
     * 添加开题报告的Mapper层方法
     *
     * @param openingReport OpeningReport对象，存有报告内容、学生id、教师id、审核标记
     * @return 受影响行数
     */
    Integer addOpeningReport(OpeningReport openingReport);

    /**
     * 根据教师id和开题报告状态查询开题报告数量的Mapper层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag);
}
