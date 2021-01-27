package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Notice;
import cn.zcbigdata.mybits_demo.mapper.NoticeMapper;
import cn.zcbigdata.mybits_demo.service.INoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class INoticeServiceImpl implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public int addNotice(Notice notice) {
        return this.noticeMapper.addNotice(notice);
    }

    @Override
    public int updateNotice(Notice notice) {
        return this.noticeMapper.updateNotice(notice);
    }

    @Override
    public int deleteNotice(int id) {
        return this.noticeMapper.deleteNotice(id);
    }

    @Override
    public List<Notice> selectAdminNotice(int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.noticeMapper.selectAdminNotice(pageIndex,limit);
    }

    @Override
    public int selectAdminNoticeCount() {
        return this.noticeMapper.selectAdminNoticeCount();
    }

    @Override
    public List<Notice> selectTeacherNotice(int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.noticeMapper.selectTeacherNotice(pageIndex, limit);
    }

    @Override
    public int selectTeacherNoticeCount() {
        return this.noticeMapper.selectTeacherNoticeCount();
    }

}
