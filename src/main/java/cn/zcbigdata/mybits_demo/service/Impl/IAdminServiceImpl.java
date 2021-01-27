package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Admin;
import cn.zcbigdata.mybits_demo.mapper.AdminMapper;
import cn.zcbigdata.mybits_demo.service.IAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yty
 */
@Service
public class IAdminServiceImpl implements IAdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin adminLogin(Admin admin) {
        return this.adminMapper.adminLogin(admin);
    }

    @Override
    public Integer resetPassword(Map<String, String> map) {
        return this.adminMapper.resetPassword(map);
    }
}
