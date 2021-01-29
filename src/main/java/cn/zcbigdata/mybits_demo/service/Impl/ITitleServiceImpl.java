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
    public int deleteTitle(int id) {
        return this.titleMapper.deleteTitle(id);
    }

    @Override
    public List<Title> selectTitleByTeacherId(int teacherid, int flag, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectTitleByTeacherId(teacherid, flag, pageIndex, limit);
    }

    @Override
    public int selectTitleCountByTeacherId(int teacherid, int flag) {
        return this.titleMapper.selectTitleCountByTeacherId(teacherid, flag);
    }

    @Override
    public List<Title> selectNotTitleByTeacherId(int teacherid, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectNotTitleByTeacherId(teacherid, pageIndex, limit);
    }

    @Override
    public int selectNotTitleCountByTeacherId(int teacherid) {
        return this.titleMapper.selectNotTitleCountByTeacherId(teacherid);
    }

    @Override
    public List<Title> selectTitleByStudentId(int studentid) {
        return this.titleMapper.selectTitleByStudentId(studentid);
    }

    @Override
    public int selectTitleCountByStudentId(int studentid) {
        return this.titleMapper.selectTitleCountByStudentId(studentid);
    }

    @Override
    public int chooseTitle(Title title) {
        return this.titleMapper.chooseTitle(title);
    }

    @Override
    public int addStuTitle(Title title) {
        return this.titleMapper.addStuTitle(title);
    }

    @Override
    public List<Title> selectStuTitle(int teacherid, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectStuTitle(teacherid, pageIndex, limit);
    }

    @Override
    public int selectStuTitleCount(int teacherid) {
        return this.titleMapper.selectStuTitleCount(teacherid);
    }

    @Override
    public int checkStuTitle(Title title) {
        return this.titleMapper.checkStuTitle(title);
    }
}
