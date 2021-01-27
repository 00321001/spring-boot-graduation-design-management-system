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

    @Override
    public Teacher teacherLogin(Teacher teacher) {
        return this.teacherMapper.teacherLogin(teacher);
    }

    @Override
    public Integer resetPassword(Map<String, String> map) {
        return this.teacherMapper.resetPassword(map);
    }

    @Override
    public Integer adminAddTeacher(Teacher teacher) {
        return this.teacherMapper.adminAddTeacher(teacher);
    }

    @Override
    public Integer adminUpdateTeacher(Teacher teacher) {
        return this.teacherMapper.adminUpdateTeacher(teacher);
    }

    @Override
    public Teacher selectTeacherById(Integer id) {
        return this.teacherMapper.selectTeacherById(id);
    }

    @Override
    public List<Teacher> selectAll(Integer page, Integer pageSize) {
        Integer startIndex = (page - 1) * pageSize;
        Map<String, Integer> map = new HashMap<>(2);
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);
        return this.teacherMapper.selectAll(map);
    }

    @Override
    public Integer selectCount() {
        return this.teacherMapper.selectCount();
    }
}
