package cn.zcbigdata.mybits_demo.mapper;

import cn.zcbigdata.mybits_demo.entity.Admin;

import java.util.Map;

/**
 * @author yty
 */
public interface AdminMapper {
    /**
     * 管理员登录的Mapper层方法
     *
     * @param admin 封装好的Admin对象，内有userName和password
     * @return 返回从数据库中查询到的Admin对象
     */
    Admin adminLogin(Admin admin);

    /**
     * 修改管理员密码的Mapper层方法
     *
     * @param map 一个Map，里面存有用户id：userid；旧密码：oldPassword； 新密码：newPassword
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    Integer resetPassword(Map<String, String> map);

    /**
     * 根据id获取管理员信息的Mapper层方法
     *
     * @param id 管理员id
     * @return Admin对象，存有查询到的信息
     */
    Admin selectAdminById(Integer id);
}
