package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.FinalAudit;
import cn.zcbigdata.mybits_demo.mapper.FinalAuditMapper;
import cn.zcbigdata.mybits_demo.service.IFinalAuditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yty
 */
@Service
public class IFinalAuditServiceImpl implements IFinalAuditService {

    @Resource
    private FinalAuditMapper finalAuditMapper;

    /**
     * 根据教师id获取论文终稿的Service层方法
     *
     * @param id    教师id
     * @param flag  终稿状态
     * @param page  当前页码
     * @param limit 每页大小
     * @return 一个List，存有查询到的FinalAudit对象
     */
    @Override
    public List<FinalAudit> selectFinalAuditByTeacherid(Integer id, Integer flag, Integer page, Integer limit) {
        Integer startIndex = (page - 1) * limit;
        return this.finalAuditMapper.selectFinalAuditByTeacherid(id, flag, startIndex, limit);
    }

    /**
     * 根据学生id获取论文终稿的Service层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的FinalAudit对象
     */
    @Override
    public List<FinalAudit> selectFinalAuditByStudent(Integer id) {
        return this.finalAuditMapper.selectFinalAuditByStudent(id);
    }

    /**
     * 教师审核论文终稿并添加评语的Service层方法
     *
     * @param finalAudit FinalAudit对象，存有论文终稿id，评语，标记
     * @return 受影响的行数
     */
    @Override
    public Integer reviewFinalAudit(FinalAudit finalAudit) {
        FinalAudit finalAuditSelected = this.finalAuditMapper.selectFinalAuditById(finalAudit.getId());
        if (!finalAuditSelected.getTeacherid().equals(finalAudit.getTeacherid())) {
            return 0;
        }
        return this.finalAuditMapper.reviewFinalAudit(finalAudit);
    }

    /**
     * 根据论文终稿id获取论文终稿的Service层方法
     *
     * @param id 论文终稿id
     * @return FinalAudit对象，存有相关信息
     */
    @Override
    public FinalAudit selectFinalAuditById(Integer id) {
        return this.finalAuditMapper.selectFinalAuditById(id);
    }

    /**
     * 添加论文终稿的Service层方法
     *
     * @param finalAudit FinalAudit对象，存有论文终稿内容、学生id、教师id
     * @return 受影响行数
     */
    @Override
    public Integer addFinalAudit(FinalAudit finalAudit) {
        List<FinalAudit> finalAudits = this.finalAuditMapper.selectFinalAuditByStudent(finalAudit.getStudentid());

        for (FinalAudit finalAudit1 : finalAudits) {
            if (finalAudit1.getFlag().equals(1) || finalAudit1.getFlag().equals(0)) {
                return 0;
            }
        }
        finalAudit.setFlag(0);
        return this.finalAuditMapper.addFinalAudit(finalAudit);
    }

    /**
     * 根据教师id和论文终稿状态查询论文终稿数量的Service层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    @Override
    public Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag) {
        return this.finalAuditMapper.selectCountByTeacherIdAndFlag(teacherid, flag);
    }
}
