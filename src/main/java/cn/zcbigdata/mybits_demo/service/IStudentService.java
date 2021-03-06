package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
public interface IStudentService {

    /**
     * 根据教师id查询学生信息的Service层方法
     *
     * @param teacherid 教师id
     * @param page      当前页码
     * @param limit     每页条数
     * @return 一个List，其中存放Student对象
     */
    List<Student> selectStudentByTeacherid(Integer teacherid, Integer page, Integer limit);

    /**
     * 教师添加单个学生接口Service层方法
     *
     * @param student Teacher对象，存有userName、nickName、teacherid
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer teacherAddStudent(Student student);

    /**
     * 教师使用xml批量添加学生的Service层方法
     *
     * @param teacherid 教师id
     * @param file      上传的xml的二进制流
     * @param file      上传的xml的二进制流
     * @param filePath  上传文件存储路径
     * @return 一个Map，存放有code：状态码；msg：提示信息
     */
    Map<String, String> teacherAddStudentsUseXml(Integer teacherid, byte[] file, String filePath);

    /**
     * 根据id修改学生信息的Service层方法
     *
     * @param student Student对象。存有id、userName、password、nickName
     * @return 受影响行数
     */
    Integer teacherUpdateStudentById(Student student);

    /**
     * 根据id获取学生信息的Service层方法
     *
     * @param studentid 学生id
     * @return 查询到的Student对象
     */
    List<Student> selectStudentById(Integer studentid);

    /**
     * 修改教师密码的Service层方法
     *
     * @param map 一个Map，里面存有用户id：userid；旧密码：oldPassword； 新密码：newPassword
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer resetPassword(Map<String, String> map);

    /**
     * 学生登录的Service层方法
     *
     * @param student Student对象，包含用户名和密码
     * @return 返回查询到的学生对象
     */
    Student studentLogin(Student student);

    /**
     * 根据教师id获取学生数量的service层方法
     *
     * @param id 教师id
     * @return 学生数量
     */
    Integer selectCountByTeacherid(Integer id);

    /**
     * 通过id删除学生的Service层方法
     *
     * @param id 学生id
     * @return 受影响行数
     */
    Integer deleteById(Integer id);

}
