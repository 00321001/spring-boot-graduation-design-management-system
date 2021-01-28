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
     *
     * @param map 一个map，其中存储当前页开始序号：startIndex；每页条数：pageSize；教师id：teacherid
     * @return 一个List，其中存放Student对象
     */
    List<Student> selectStudentByTeacherid(Map<String, Integer> map);

    /**
     * 教师添加学生接口Mapper层方法
     *
     * @param student Teacher对象，存有userName、nickName、teacherid
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer teacherAddStudent(Student student);

    /**
     * 根据id删除学生的Mapper层方法
     *
     * @param id 学生id
     * @return 受影响行数
     */
    Integer deleteStudentById(Integer id);

    /**
     * 根据id修改学生信息的Mapper层方法
     *
     * @param student Student对象。存有id、userName、password、nickName
     * @return 受影响行数
     */
    Integer teacherUpdateStudentById(Student student);

    /**
     * 根据id获取学生信息的Mapper层方法
     *
     * @param studentid 学生id
     * @return 查询到的Student对象
     */
    Student selectStudentById(Integer studentid);

    /**
     * 学生登录的Mapper层方法
     * @param student Student对象，包含用户名和密码
     * @return 返回查询到的学生对象
     */
    Student studentLogin(Student student);
}
