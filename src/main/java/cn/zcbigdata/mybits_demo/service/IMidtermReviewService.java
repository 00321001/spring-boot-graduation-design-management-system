package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.MidtermReview;

import java.util.List;

public interface IMidtermReviewService {
    List<MidtermReview> selectMidtermByTeacherId(int teacherid, int flag,int page, int limit);

    int selectMidtermCountByTeacherId(int teacherid,int flag);

    List<MidtermReview> selectMidtermByStudentId(int studentid);

    int checkMidterm(MidtermReview midtermReview);

    int selectMidtermCountByStudentId(int studentid);

    int addMidterm(MidtermReview midtermReview);

    List<MidtermReview> selectMidtermById(int id);
}
