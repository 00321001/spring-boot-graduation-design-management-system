package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Title;
import cn.zcbigdata.mybits_demo.mapper.TitleMapper;
import cn.zcbigdata.mybits_demo.service.ITitleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ITitleServiceImpl implements ITitleService {

    @Resource
    private TitleMapper titleMapper;


    @Override
    public int addTitle(Title title) {
        return this.titleMapper.addTitle(title);
    }

    @Override
    public int updateTitle(Title title) {
        return this.titleMapper.updateTitle(title);
    }

    @Override
    public List<Title> selectTitleById(int teacherid, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectTitleById(teacherid, pageIndex, limit);
    }

    @Override
    public int selectTitleCountById(int teacherid) {
        return this.titleMapper.selectTitleCountById(teacherid);
    }
}
