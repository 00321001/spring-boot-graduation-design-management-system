package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Title;

import java.util.List;

public interface ITitleService {
    int addTitle(Title title);

    int updateTitle(Title title);

    List<Title> selectTitleById(int teacherid, int page, int limit);

    int selectTitleCountById(int teacherid);
}
