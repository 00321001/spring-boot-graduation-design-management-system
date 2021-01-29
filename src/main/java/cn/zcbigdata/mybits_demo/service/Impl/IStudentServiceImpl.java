package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Student;
import cn.zcbigdata.mybits_demo.mapper.StudentMapper;
import cn.zcbigdata.mybits_demo.service.IStudentService;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author yty
 */
@Service
public class IStudentServiceImpl implements IStudentService {

    private static final Logger logger = Logger.getLogger(IStudentServiceImpl.class);
    @Resource
    private StudentMapper studentMapper;

    /**
     * 根据教师id查询学生信息的Service层方法
     *
     * @param teacherid 教师id
     * @param page      当前页码
     * @param limit     每页条数
     * @return 一个List，其中存放Student对象
     */
    @Override
    public List<Student> selectStudentByTeacherid(Integer teacherid, Integer page, Integer limit) {
        Integer startIndex = (page - 1) * limit;
        Map<String, Integer> map = new HashMap<>(3);
        map.put("startIndex", startIndex);
        map.put("pageSize", limit);
        map.put("teacherid", teacherid);
        return this.studentMapper.selectStudentByTeacherid(map);
    }

    /**
     * 教师添加单个学生接口Service层方法
     *
     * @param student Teacher对象，存有userName、nickName、teacherid
     * @return 受影响的行数，返回0时代表操作失败，1代表操作成功
     */
    @Override
    public Integer teacherAddStudent(Student student) {
        return this.studentMapper.teacherAddStudent(student);
    }

    /**
     * 教师使用xml批量添加学生的Service层方法
     *
     * @param teacherid 教师id
     * @param file      上传的xml的二进制流
     * @param filePath  上传文件存储路径
     * @return 一个Map，存放有code：状态码；msg：提示信息
     */
    @Override
    public Map<String, String> teacherAddStudentsUseXml(Integer teacherid, byte[] file, String filePath) {
        Map<String, String> map = new HashMap<>(2);
        String msg;
        File fileSavePath = new File(filePath);
        //创建文件存储路径
        if (!fileSavePath.exists()) {
            logger.info("路径" + filePath + "不存在，即将创建");
            boolean flag = fileSavePath.mkdirs();
            if (flag) {
                logger.info("路径创建成功！");
            } else {
                logger.info("路径创建失败！");
                map.put("code", "9999");
                map.put("msg", "路径创建失败!");
                return map;
            }
        }
        //重命名文件，防止出现重名
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".xml";
        logger.info("文件重命名为：" + fileName);
        try {
            //使用文件输出流创建并写入xml文件
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
            fileOutputStream.write(file);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            msg = "写入文件失败，详情查看日志";
            logger.info(msg);
            logger.error(e);
            map.put("code", "9999");
            map.put("msg", msg);
            return map;
        }
        File xmlFile = new File(filePath + fileName);
        try {
            SAXReader saxReader = new SAXReader();
            //加载xml文档对象
            Document document = saxReader.read(xmlFile);
            //获取根节点对象
            Element rootElement = document.getRootElement();
            //获取子节点对象的集合
            List<Element> elements = rootElement.elements();
            Student student = new Student();
            student.setTeacherid(teacherid);
            //遍历集合，为对象赋值
            int flag = 0;
            Integer[] keyList = new Integer[elements.size()];
            for (Element studentInfo : elements) {
                student.setUserName(studentInfo.element("userName").getText());
                student.setNickName(studentInfo.element("nickName").getText());
                try {
                    this.studentMapper.teacherAddStudent(student);
                    keyList[flag] = student.getId();
                    flag++;
                } catch (Exception e) {
                    //回滚插入（删除刚刚已经插入的数据）
                    for (int i = flag; i >= 0; i--) {
                        this.studentMapper.deleteStudentById(keyList[i]);
                    }
                    this.deleteFile(xmlFile);
                    msg = "插入数据库出现问题，可能是因为用户名冲突，出错位置为第" + (flag + 1) + "条数据，userName = " + student.getUserName() + "，已回滚操作";
                    logger.info(msg);
                    logger.error(e);
                    map.put("code", "9999");
                    map.put("msg", msg);
                    return map;
                }
            }
        } catch (Exception e) {
            msg = "解析xml出现问题，请检查xml文件格式";
            logger.info(msg);
            logger.error(e);
            map.put("code", "9999");
            map.put("msg", msg);
            return map;
        }
        if (deleteFile(xmlFile)) {
            msg = "操作成功";
            logger.info(msg);
            map.put("code", "0000");
        } else {
            msg = "数据库插入成功，删除文件出现问题，详见日志";
            logger.info(msg);
            map.put("code", "9999");
        }
        map.put("msg", msg);
        return map;
    }

    /**
     * 根据id修改学生信息的Service层方法
     *
     * @param student Student对象。存有id、userName、password、nickName
     * @return 受影响行数
     */
    @Override
    public Integer teacherUpdateStudentById(Student student) {
        try {
            return this.studentMapper.teacherUpdateStudentById(student);
        }catch (Exception e){
            logger.error(e);
            return 0;
        }
    }

    /**
     * 根据id获取学生信息的Service层方法
     *
     * @param studentid 学生id
     * @return 查询到的Student对象
     */
    @Override
    public Student selectStudentById(Integer studentid) {
        return this.studentMapper.selectStudentById(studentid);
    }

    /**
     * 学生登录的Service层方法
     *
     * @param student Student对象，包含用户名和密码
     * @return 返回查询到的学生对象
     */
    @Override
    public Student studentLogin(Student student) {
        return this.studentMapper.studentLogin(student);
    }

    /**
     * 根据教师id获取学生数量的service层方法
     *
     * @param id 教师id
     * @return 学生数量
     */
    @Override
    public Integer selectCountByTeacherid(Integer id) {
        return this.studentMapper.selectCountByTeacherid(id);
    }

    /**
     * 通过id删除学生的Service层方法
     *
     * @param id 学生id
     * @return 受影响行数
     */
    @Override
    public Integer deleteById(Integer id) {
        return this.studentMapper.deleteById(id);
    }

    /**
     * 删除文件的方法
     *
     * @param file File对象
     * @return 删除成功返回true，失败返回false
     */
    private boolean deleteFile(File file) {
        try {
            return file.delete();
        } catch (Exception e) {
            logger.error("删除错误日志" + e);
            return false;
        }
    }
}
