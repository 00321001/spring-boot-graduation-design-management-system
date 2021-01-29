package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.MidtermReview;
import cn.zcbigdata.mybits_demo.mapper.MidtermReviewMapper;
import cn.zcbigdata.mybits_demo.service.IMidtermReviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IMidtermReviewServiceImpl implements IMidtermReviewService {

    @Resource
    private MidtermReviewMapper midtermReviewMapper;

    @Override
    public List<MidtermReview> selectMidtermByTeacherId(int teacherid, int flag ,int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.midtermReviewMapper.selectMidtermByTeacherId(teacherid, flag,pageIndex, limit);
    }

    @Override
    public int selectMidtermCountByTeacherId(int teacherid,int flag) {
        return this.midtermReviewMapper.selectMidtermCountByTeacherId(teacherid,flag);
    }

    @Override
    public List<MidtermReview> selectMidtermByStudentId(int studentid) {
        return this.midtermReviewMapper.selectMidtermByStudentId(studentid);
    }

    @Override
    public int checkMidterm(MidtermReview midtermReview) {
        return this.midtermReviewMapper.checkMidterm(midtermReview);
    }

    @Override
    public int addComments(MidtermReview midtermReview) {
        return this.midtermReviewMapper.addComments(midtermReview);
    }

    @Override
    public int selectMidtermCountByStudentId(int studentid) {
        return this.midtermReviewMapper.selectMidtermCountByStudentId(studentid);
    }

    @Override
    public int addMidterm(MidtermReview midtermReview) {
        return this.midtermReviewMapper.addMidterm(midtermReview);
    }

    @Override
    public List<MidtermReview> selectMidtermById(int id) {
        return this.midtermReviewMapper.selectMidtermById(id);
    }

    @Override
    public int deleteMidterm(int id) {
        return this.midtermReviewMapper.deleteMidterm(id);
    }

}
