package cn.zcbigdata.mybits_demo.utils;

import javax.servlet.http.HttpSession;

/**
 * 工具类
 * @author yty
 */
public class UtilTools {
    /**
     * 用于判空的方法
     * @param args 将参数整合为String数组传入
     * @return 有空值时返回false，没有空值返回true
     */
    public static boolean checkNull(String[] args) {
        for (String arg : args) {
            if (arg == null || arg.trim().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用于检查是否登录的方法
     * @param session 一个HttpSession对象
     * @param type 期望的用户类型：管理员-admin；教师-teacher；学生-student
     * @return 用户已经登录返回true，否则返回false
     */
    public static boolean checkLogin(HttpSession session, String type) {
        String useridStr = (String) session.getAttribute("userid");
        return useridStr != null && useridStr.length() > 0;
    }

    /**
     * 参数为空的返回值
     */
    public static final String IS_NULL_RETURN_JSON = "{\"code\":\"9999\",\"msg\":\"缺少参数\"}";
    /**
     * 成功的返回值
     */
    public static final String SUCCESS_RETURN_JSON = "{\"code\":\"0000\",\"msg\":\"操作成功\"}";
    /**
     * 失败的返回值
     */
    public static final String FAIL_RETURN_JSON = "{\"code\":\"9999\",\"msg\":\"操作失败\"}";
    /**
     * 没有登录的返回值
     */
    public static final String NO_LOGIN_RETURN_JSON = "{\"code\":\"7777\",\"msg\":\"没有登录\"}";

}
