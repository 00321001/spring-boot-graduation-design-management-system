package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.Student;
import cn.zcbigdata.mybits_demo.entity.Title;
import cn.zcbigdata.mybits_demo.mapper.TitleMapper;
import cn.zcbigdata.mybits_demo.service.ITitleService;
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

@Service
public class ITitleServiceImpl implements ITitleService {

    private static final Logger logger = Logger.getLogger(IStudentServiceImpl.class);
    @Resource
    private TitleMapper titleMapper;


    @Override
    public int addTitle(Title title) {
        return this.titleMapper.addTitle(title);
    }

    @Override
    public int updateTitle(Title title) {
        return this.titleMapper.updateTitle(title);
    }

    @Override
    public int deleteTitle(int id) {
        return this.titleMapper.deleteTitle(id);
    }

    @Override
    public List<Title> selectTitleByTeacherId(int teacherid, int flag, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectTitleByTeacherId(teacherid, flag, pageIndex, limit);
    }

    @Override
    public int selectTitleCountByTeacherId(int teacherid, int flag) {
        return this.titleMapper.selectTitleCountByTeacherId(teacherid, flag);
    }

    @Override
    public List<Title> selectNotTitleByTeacherId(int teacherid, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectNotTitleByTeacherId(teacherid, pageIndex, limit);
    }

    @Override
    public int selectNotTitleCountByTeacherId(int teacherid) {
        return this.titleMapper.selectNotTitleCountByTeacherId(teacherid);
    }

    @Override
    public List<Title> selectTitleByStudentId(int studentid,int page,int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectTitleByStudentId(studentid,pageIndex,limit);
    }

    @Override
    public int selectTitleCountByStudentId(int studentid) {
        return this.titleMapper.selectTitleCountByStudentId(studentid);
    }

    @Override
    public int selectTitleCountByStuId(int studentid) {
        return this.titleMapper.selectTitleCountByStuId(studentid);
    }

    @Override
    public int chooseTitle(Title title) {
        return this.titleMapper.chooseTitle(title);
    }

    @Override
    public int addStuTitle(Title title) {
        return this.titleMapper.addStuTitle(title);
    }

    @Override
    public List<Title> selectStuTitle(int teacherid, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.titleMapper.selectStuTitle(teacherid, pageIndex, limit);
    }

    @Override
    public int selectStuTitleCount(int teacherid) {
        return this.titleMapper.selectStuTitleCount(teacherid);
    }

    @Override
    public int checkStuTitle(Title title) {
        return this.titleMapper.checkStuTitle(title);
    }

    @Override
    public Map<String, String> teacherAddTitleUseXml(Integer teacherid, byte[] file, String filePath) {
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
            Title title = new Title();
            title.setTeacherid(teacherid);
            //遍历集合，为对象赋值
            int flag = 0;
            Integer[] keyList = new Integer[elements.size()];
            for (Element titleInfo : elements) {
                title.setTitle(titleInfo.element("title").getText());
                try {
                    this.titleMapper.addTitle(title);
                    keyList[flag] = title.getId();
                    flag++;
                } catch (Exception e) {
                    //回滚插入（删除刚刚已经插入的数据）
                    for (int i = flag; i >= 0; i--) {
                        this.titleMapper.deleteTitle(keyList[i]);
                    }
                    this.deleteFile(xmlFile);
                    msg = "插入数据库出现问题，可能是因为用户名冲突，出错位置为第" + (flag + 1) + "条数据，title = " + title.getTitle() + "，已回滚操作";
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
