package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.MidtermReview;

import java.util.List;

public interface MidtermReviewMapper {
    List<MidtermReview> selectMidtermByTeacherId(int teacherid, int flag, int page, int limit);

    int selectMidtermCountByTeacherId(int teacherid, int flag);

    List<MidtermReview> selectMidtermByStudentId(int studentid);

    int checkMidterm(MidtermReview midtermReview);

    int addComments(MidtermReview midtermReview);

    int selectMidtermCountByStudentId(int studentid);

    int selectMidtermCountByStuId(int studentid);

    int addMidterm(MidtermReview midtermReview);

    List<MidtermReview> selectMidtermById(int id);

    int deleteMidterm(int id);
}
