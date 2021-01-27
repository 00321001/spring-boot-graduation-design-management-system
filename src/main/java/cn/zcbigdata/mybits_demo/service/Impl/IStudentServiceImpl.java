package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Student;
import cn.zcbigdata.mybits_demo.mapper.StudentMapper;
import cn.zcbigdata.mybits_demo.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yty
 */
@Service
public class IStudentServiceImpl implements IStudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<Student> selectStudentByTeacherid(Integer teacherid, Integer page, Integer limit) {
        Integer startIndex = (page - 1) * limit;
        Map<String, Integer> map= new HashMap<>(3);
        map.put("startIndex",startIndex);
        map.put("pageSize", limit);
        map.put("teacherid", teacherid);
        return this.studentMapper.selectStudentByTeacherid(map);
    }
}
