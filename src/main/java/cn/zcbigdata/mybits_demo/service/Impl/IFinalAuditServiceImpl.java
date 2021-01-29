package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.FinalAudit;
import cn.zcbigdata.mybits_demo.entity.OpeningReport;
import cn.zcbigdata.mybits_demo.mapper.FinalAuditMapper;
import cn.zcbigdata.mybits_demo.service.IFinalAuditService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author yty
 */
@Service
public class IFinalAuditServiceImpl implements IFinalAuditService {

    private static final Logger logger = Logger.getLogger(IFinalAuditServiceImpl.class);
    @Resource
    private FinalAuditMapper finalAuditMapper;

    /**
     * 根据教师id获取论文终稿的Service层方法
     *
     * @param id    教师id
     * @param flag  终稿状态
     * @param page  当前页码
     * @param limit 每页大小
     * @return 一个List，存有查询到的FinalAudit对象
     */
    @Override
    public List<FinalAudit> selectFinalAuditByTeacherid(Integer id, Integer flag, Integer page, Integer limit) {
        Integer startIndex = (page - 1) * limit;
        return this.finalAuditMapper.selectFinalAuditByTeacherid(id, flag, startIndex, limit);
    }

    /**
     * 根据学生id获取论文终稿的Service层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的FinalAudit对象
     */
    @Override
    public List<FinalAudit> selectFinalAuditByStudent(Integer id) {
        return this.finalAuditMapper.selectFinalAuditByStudent(id);
    }

    /**
     * 教师审核论文终稿并添加评语的Service层方法
     *
     * @param finalAudit FinalAudit对象，存有论文终稿id，评语，标记
     * @return 受影响的行数
     */
    @Override
    public Integer reviewFinalAudit(FinalAudit finalAudit) {
        FinalAudit finalAuditSelected = this.finalAuditMapper.selectFinalAuditById(finalAudit.getId());
        if (!finalAuditSelected.getTeacherid().equals(finalAudit.getTeacherid())) {
            return 0;
        }
        return this.finalAuditMapper.reviewFinalAudit(finalAudit);
    }

    /**
     * 根据论文终稿id获取论文终稿的Service层方法
     *
     * @param id 论文终稿id
     * @return FinalAudit对象，存有相关信息
     */
    @Override
    public FinalAudit selectFinalAuditById(Integer id) {
        return this.finalAuditMapper.selectFinalAuditById(id);
    }

    /**
     * 添加论文终稿的Service层方法
     *
     * @param finalAudit FinalAudit对象，存有论文终稿内容、学生id、教师id
     * @return 受影响行数
     */
    @Override
    public Integer addFinalAudit(FinalAudit finalAudit) {
        List<FinalAudit> finalAudits = this.finalAuditMapper.selectFinalAuditByStudent(finalAudit.getStudentid());

        for (FinalAudit finalAudit1 : finalAudits) {
            if (finalAudit1.getFlag().equals(1) || finalAudit1.getFlag().equals(0)) {
                return 0;
            }
        }
        finalAudit.setFlag(0);
        return this.finalAuditMapper.addFinalAudit(finalAudit);
    }

    /**
     * 根据教师id和论文终稿状态查询论文终稿数量的Service层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    @Override
    public Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag) {
        return this.finalAuditMapper.selectCountByTeacherIdAndFlag(teacherid, flag);
    }

    /**
     * 下载开题报告的service层方法
     *
     * @param response HttpServletResponse
     * @param id       开题报告id
     * @return 存有状态码和提示信息的集合
     */
    @Override
    public Map<String, String> downloadFinalAudit(HttpServletResponse response, Integer id) {
        FinalAudit finalAudit = this.selectFinalAuditById(id);
        //拼接内容和教师评语（评分）
        String content = finalAudit.getContent() + "\n-----------------以下为教师评语-----------------\n" + finalAudit.getComments();
        //生成随机的文件名
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".txt";
        //设置response参数
        response.setContentType("application/force-download");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName));
        //存放返回值的Map
        Map<String, String> map= new HashMap<String, String>(2);
        //声明缓冲流，输出流等
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try{
            //获取response的输出流
            outputStream = response.getOutputStream();
            //设置缓冲流
            bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            int read = bis.read(buff);
            //循环读取并输出
            while (read != -1){
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
            map.put("code","0000");
            map.put("msg", "下载成功");
        }catch (Exception e){
            logger.error(e);
            map.put("code","9999");
            map.put("msg", "下载失败");
        }finally {
            //关闭流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
