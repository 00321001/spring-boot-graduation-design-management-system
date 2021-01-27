package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Teacher;

import java.util.Map;

/**
 * @author yty
 */
public interface ITeacherService {
    /**
     * 教师登录的Service层方法
     *
     * @param teacher Teacher对象，内有userName和password
     * @return 返回从数据库中查询到的Teacher对象
     */
    Teacher teacherLogin(Teacher teacher);

    /**
     * 修改教师密码的Service层方法
     *
     * @param map 一个Map，里面存有用户id：userid；旧密码：oldPassword； 新密码：newPassword
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer resetPassword(Map<String, String> map);
}
