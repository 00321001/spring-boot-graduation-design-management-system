package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Notice;

import java.util.List;

public interface INoticeService {
    int addNotice(Notice notice);

    int updateNotice(Notice notice);

    int deleteNotice(int id);

    List<Notice> selectAdminNotice(int page, int limit);

    int selectAdminNoticeCount();

    List<Notice> selectTeacherNotice(int page, int limit);

    int selectTeacherNoticeCount();
}
