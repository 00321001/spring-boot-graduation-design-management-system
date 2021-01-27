package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Notice;

import java.util.List;

public interface INoticeService {
    int adminAddNotice(Notice notice);

    int adminUpdateNotice(Notice notice);

    int adminDeleteNotice(int id);

    List<Notice> selectAdminNotice(int page, int limit);

    int selectAdminNoticeCount();
}
