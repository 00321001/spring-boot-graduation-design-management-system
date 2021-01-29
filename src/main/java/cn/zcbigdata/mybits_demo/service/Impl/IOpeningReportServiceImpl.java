package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.OpeningReport;
import cn.zcbigdata.mybits_demo.mapper.OpeningReportMapper;
import cn.zcbigdata.mybits_demo.service.IOpeningReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yty
 */
@Service
public class IOpeningReportServiceImpl implements IOpeningReportService {

    @Resource
    private OpeningReportMapper openingReportMapper;

    /**
     * 根据教师id获取开题报告的Service层方法
     *
     * @param id   教师id
     * @param flag 开题报告状态
     * @return 一个List，存有查询到的OpeningReport对象
     */
    @Override
    public List<OpeningReport> selectOpeningReportByTeacherid(Integer id, Integer flag, Integer page, Integer limit) {
        Integer startIndex = (page - 1) * limit;
        return this.openingReportMapper.selectOpeningReportByTeacherid(id, flag, startIndex, limit);
    }

    /**
     * 根据学生id获取开题报告的Service层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的OpeningReport对象
     */
    @Override
    public List<OpeningReport> selectOpeningReportByStudent(Integer id) {
        return this.openingReportMapper.selectOpeningReportByStudent(id);
    }

    /**
     * 教师审核开题报告并添加评语的Service层方法
     *
     * @param openingReport OpeningReport对象，存有报告id，评语，标记
     * @return 受影响的行数
     */
    @Override
    public Integer reviewOpeningReport(OpeningReport openingReport) {
        OpeningReport openingReportSelected = this.openingReportMapper.selectOpeningReportById(openingReport.getId());
        if (!openingReportSelected.getTeacherid().equals(openingReport.getTeacherid())) {
            return 0;
        }
        return this.openingReportMapper.reviewOpeningReport(openingReport);
    }

    /**
     * 根据开题报告id获取开题报告的Service层方法
     *
     * @param id 开题报告id
     * @return OpeningReport对象，存有相关信息
     */
    @Override
    public OpeningReport selectOpeningReportById(Integer id) {
        return this.openingReportMapper.selectOpeningReportById(id);
    }

    /**
     * 添加开题报告的Service层方法
     *
     * @param openingReport OpeningReport对象，存有报告内容、学生id、教师id
     * @return 受影响行数
     */
    @Override
    public Integer addOpeningReport(OpeningReport openingReport) {
        List<OpeningReport> openingReports = this.openingReportMapper.selectOpeningReportByStudent(openingReport.getStudentid());
        for (OpeningReport openingReport1 : openingReports) {
            if (openingReport1.getFlag().equals(1) || openingReport1.getFlag().equals(0)) {
                return 0;
            }
        }
        openingReport.setFlag(0);
        return this.openingReportMapper.addOpeningReport(openingReport);
    }

    /**
     * 根据教师id和开题报告状态查询开题报告数量的Service层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    @Override
    public Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag) {
        return this.openingReportMapper.selectCountByTeacherIdAndFlag(teacherid, flag);
    }
}
