package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Teacher;
import cn.zcbigdata.mybits_demo.mapper.TeacherMapper;
import cn.zcbigdata.mybits_demo.service.ITeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
@Service
public class ITeacherServiceImpl implements ITeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 教师登录的Service层方法
     *
     * @param teacher Teacher对象，内有userName和password
     * @return 返回从数据库中查询到的Teacher对象
     */
    @Override
    public Teacher teacherLogin(Teacher teacher) {
        return this.teacherMapper.teacherLogin(teacher);
    }

    /**
     * 修改教师密码的Service层方法
     *
     * @param map 一个Map，里面存有用户id：userid；旧密码：oldPassword； 新密码：newPassword
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    @Override
    public Integer resetPassword(Map<String, String> map) {
        return this.teacherMapper.resetPassword(map);
    }

    /**
     * 管理员添加教师接口Service层方法
     *
     * @param teacher Teacher对象，存有userName、nickName
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    @Override
    public Integer adminAddTeacher(Teacher teacher) {
        return this.teacherMapper.adminAddTeacher(teacher);
    }

    /**
     * 管理员修改教师信息接口的Service层方法
     *
     * @param teacher Teacher对象，存有id、userName、password、nickName
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    @Override
    public Integer adminUpdateTeacher(Teacher teacher) {
        return this.teacherMapper.adminUpdateTeacher(teacher);
    }

    /**
     * 根据id查询教师信息接口Service层方法
     *
     * @param id 教师id
     * @return id对应的Teacher对象
     */
    @Override
    public Teacher selectTeacherById(Integer id) {
        return this.teacherMapper.selectTeacherById(id);
    }

    /**
     * 查询所有教师信息的Service层方法，用于分页查询
     *
     * @param page     当前页码
     * @param pageSize 每页多少数据
     * @return 一个List，内存有若干Teacher对象
     */
    @Override
    public List<Teacher> selectAll(Integer page, Integer pageSize) {
        Integer startIndex = (page - 1) * pageSize;
        Map<String, Integer> map = new HashMap<>(2);
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);
        return this.teacherMapper.selectAll(map);
    }

    /**
     * 查询教师总数的Service层方法，用于分页查询
     *
     * @return 返回教师总数
     */
    @Override
    public Integer selectCount() {
        return this.teacherMapper.selectCount();
    }
}
