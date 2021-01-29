package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.Title;

import java.util.List;

public interface TitleMapper {

    int addTitle(Title title);

    int updateTitle(Title title);

    int deleteTitle(int id);

    List<Title> selectTitleByTeacherId(int teacherid,int flag, int page, int limit);

    int selectTitleCountByTeacherId(int teacherid,int flag);

    List<Title> selectNotTitleByTeacherId(int teacherid, int page, int limit);

    int selectNotTitleCountByTeacherId(int teacherid);

    List<Title> selectTitleByStudentId(int studentid);

    int selectTitleCountByStudentId(int studentid);

    int chooseTitle(Title title);

    int addStuTitle(Title title);

    List<Title> selectStuTitle(int teacherid, int page, int limit);

    int selectStuTitleCount(int teacherid);

    int checkStuTitle(Title title);

}
