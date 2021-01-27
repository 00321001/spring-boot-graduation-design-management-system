package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Student;

import java.util.List;

/**
 * @author yty
 */
public interface IStudentService {

    /**
     * 根据教师id查询学生信息的Service层方法
     * @param teacherid 教师id
     * @param page 当前页码
     * @param limit 每页条数
     * @return 一个List，其中存放Student对象
     */
    List<Student> selectStudentByTeacherid(Integer teacherid, Integer page, Integer limit);
}
