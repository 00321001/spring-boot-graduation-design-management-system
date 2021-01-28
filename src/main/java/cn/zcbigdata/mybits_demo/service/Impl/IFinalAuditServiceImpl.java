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
     * @param id 教师id
     * @return 一个List，存有查询到的FinalAudit对象
     */
    @Override
    public List<FinalAudit> selectFinalAuditByTeacherid(Integer id) {
        return this.finalAuditMapper.selectFinalAuditByTeacherid(id);
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
        List<FinalAudit> finalAudits = this.finalAuditMapper.selectFinalAuditByStudent(finalAudit.getId());
        for (FinalAudit finalAudit1 : finalAudits) {
            if (finalAudit1.getFlag().equals(1) || finalAudit1.getFlag().equals(0)) {
                return 0;
            }
        }
        finalAudit.setFlag(0);
        return this.finalAuditMapper.addFinalAudit(finalAudit);
    }
}
