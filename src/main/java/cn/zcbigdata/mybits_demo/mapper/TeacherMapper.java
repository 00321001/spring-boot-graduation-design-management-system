package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
public interface TeacherMapper {

    /**
     * 教师登录的Mapper层方法
     *
     * @param teacher Teacher对象，内有userName和password
     * @return 返回从数据库中查询到的Teacher对象
     */
    Teacher teacherLogin(Teacher teacher);

    /**
     * 修改教师密码的Mapper层方法
     *
     * @param map 一个Map，里面存有用户id：userid；旧密码：oldPassword； 新密码：newPassword
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer resetPassword(Map<String, String> map);

    /**
     * 管理员添加教师接口Mapper层方法
     * @param teacher Teacher对象，存有userName、nickName
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer adminAddTeacher(Teacher teacher);

    /**
     * 管理员修改教师信息接口的Mapper层方法
     * @param teacher Teacher对象，存有id、userName、password、nickName
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer adminUpdateTeacher(Teacher teacher);

    /**
     * 根据id查询教师信息接口Mapper层方法
     * @param id 教师id
     * @return id对应的Teacher对象
     */
    Teacher selectTeacherById(Integer id);

    /**
     * 查询所有教师信息的Mapper层方法，用于分页查询
     * @param map 一个Map，里面存有当前页起始位置：startIndex；每页的大小：pageSize
     * @return 一个List，内存有若干Teacher对象
     */
    List<Teacher> selectAll(Map<String, Integer> map);

    /**
     * 查询教师总数的Mapper层方法，用于分页查询
     * @return 返回教师总数
     */
    Integer selectCount();

}
