package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.FinalAudit;

import java.util.List;

/**
 * @author yty
 */
public interface IFinalAuditService {

    /**
     * 根据教师id获取论文终稿的Service层方法
     *
     * @param id    教师id
     * @param flag  终稿状态
     * @param page  当前页码
     * @param limit 每页大小
     * @return 一个List，存有查询到的FinalAudit对象
     */
    List<FinalAudit> selectFinalAuditByTeacherid(Integer id, Integer flag, Integer page, Integer limit);

    /**
     * 根据学生id获取论文终稿的Service层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的FinalAudit对象
     */
    List<FinalAudit> selectFinalAuditByStudent(Integer id);

    /**
     * 教师审核论文终稿并添加评语的Service层方法
     *
     * @param finalAudit FinalAudit对象，存有论文终稿id，评语，标记
     * @return 受影响的行数
     */
    Integer reviewFinalAudit(FinalAudit finalAudit);

    /**
     * 根据开题报告id获取论文终稿的Service层方法
     *
     * @param id 开题报告id
     * @return FinalAudit对象，存有相关信息
     */
    FinalAudit selectFinalAuditById(Integer id);

    /**
     * 添加论文终稿的Service层方法
     *
     * @param finalAudit FinalAudit对象，存有论文终稿内容、学生id、教师id
     * @return 受影响行数
     */
    Integer addFinalAudit(FinalAudit finalAudit);

    /**
     * 根据教师id和论文终稿状态查询论文终稿数量的Service层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag);
}
