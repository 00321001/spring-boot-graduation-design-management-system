package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
public interface StudentMapper {
    /**
     * 根据教师id查询学生信息的Mapper层方法
     * @param map 一个map，其中存储当前页开始序号：startIndex；每页条数：pageSize；教师id：teacherid
     * @return 一个List，其中存放Student对象
     */
    List<Student> selectStudentByTeacherid(Map<String, Integer> map);

}
