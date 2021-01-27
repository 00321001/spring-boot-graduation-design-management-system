package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.Notice;

import java.util.List;

public interface NoticeMapper {

    int addNotice(Notice notice);

    int updateNotice(Notice notice);

    int deleteNotice(int id);

    List<Notice> selectAdminNotice(int page,int limit);

    int selectAdminNoticeCount();

    List<Notice> selectTeacherNotice(int page,int limit);

    int selectTeacherNoticeCount();

}
