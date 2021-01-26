package cn.zcbigdata.mybits_demo.service;

import cn.zcbigdata.mybits_demo.entity.Admin;

/**
 * @author yty
 */
public interface IAdminService {
    /**
     * 管理员登录的Service层方法
     * @param admin 封装好的Admin对象，内有userName和password
     * @return 返回从数据库中查询到的Admin对象
     */
    Admin adminLogin(Admin admin);
}
