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

    /**
     * 管理员登录的Service层方法
     *
     * @param admin 封装好的Admin对象，内有userName和password
     * @return 返回从数据库中查询到的Admin对象
     */
    @Override
    public Admin adminLogin(Admin admin) {
        return this.adminMapper.adminLogin(admin);
    }

    /**
     * 修改管理员密码的Service层方法
     *
     * @param map 一个Map，里面存有用户id：userid；旧密码：oldPassword； 新密码：newPassword
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    @Override
    public Integer resetPassword(Map<String, String> map) {
        return this.adminMapper.resetPassword(map);
    }
}
