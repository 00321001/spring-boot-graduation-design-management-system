package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Teacher;
import cn.zcbigdata.mybits_demo.mapper.TeacherMapper;
import cn.zcbigdata.mybits_demo.service.ITeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
