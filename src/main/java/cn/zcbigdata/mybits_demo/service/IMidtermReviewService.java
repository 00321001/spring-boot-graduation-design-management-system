package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.MidtermReview;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IMidtermReviewService {
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

    /**
     * 下载开题报告的service层方法
     * @param response HttpServletResponse
     * @param id 开题报告id
     * @return 存有状态码和提示信息的集合
     */
    Map<String, String> downloadMidterm(HttpServletResponse response, Integer id);
}
