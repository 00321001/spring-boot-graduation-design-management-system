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
    public int adminAddNotice(Notice notice) {
        return this.noticeMapper.adminAddNotice(notice);
    }

    @Override
    public int adminUpdateNotice(Notice notice) {
        return this.noticeMapper.adminUpdateNotice(notice);
    }

    @Override
    public int adminDeleteNotice(int id) {
        return this.noticeMapper.adminDeleteNotice(id);
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

}
