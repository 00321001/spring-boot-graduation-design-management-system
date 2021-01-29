package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.OpeningReport;
import cn.zcbigdata.mybits_demo.mapper.OpeningReportMapper;
import cn.zcbigdata.mybits_demo.service.IOpeningReportService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
public class IOpeningReportServiceImpl implements IOpeningReportService {
    private static final Logger logger = Logger.getLogger(IOpeningReportServiceImpl.class);

    @Resource
    private OpeningReportMapper openingReportMapper;

    /**
     * 根据教师id获取开题报告的Service层方法
     *
     * @param id   教师id
     * @param flag 开题报告状态
     * @return 一个List，存有查询到的OpeningReport对象
     */
    @Override
    public List<OpeningReport> selectOpeningReportByTeacherid(Integer id, Integer flag, Integer page, Integer limit) {
        Integer startIndex = (page - 1) * limit;
        return this.openingReportMapper.selectOpeningReportByTeacherid(id, flag, startIndex, limit);
    }

    /**
     * 根据学生id获取开题报告的Service层方法
     *
     * @param id 学生id
     * @return 一个List，存有查询到的OpeningReport对象
     */
    @Override
    public List<OpeningReport> selectOpeningReportByStudent(Integer id) {
        return this.openingReportMapper.selectOpeningReportByStudent(id);
    }

    /**
     * 教师审核开题报告并添加评语的Service层方法
     *
     * @param openingReport OpeningReport对象，存有报告id，评语，标记
     * @return 受影响的行数
     */
    @Override
    public Integer reviewOpeningReport(OpeningReport openingReport) {
        OpeningReport openingReportSelected = this.openingReportMapper.selectOpeningReportById(openingReport.getId());
        if (!openingReportSelected.getTeacherid().equals(openingReport.getTeacherid())) {
            return 0;
        }
        return this.openingReportMapper.reviewOpeningReport(openingReport);
    }

    /**
     * 根据开题报告id获取开题报告的Service层方法
     *
     * @param id 开题报告id
     * @return OpeningReport对象，存有相关信息
     */
    @Override
    public OpeningReport selectOpeningReportById(Integer id) {
        return this.openingReportMapper.selectOpeningReportById(id);
    }

    /**
     * 添加开题报告的Service层方法
     *
     * @param openingReport OpeningReport对象，存有报告内容、学生id、教师id
     * @return 受影响行数
     */
    @Override
    public Integer addOpeningReport(OpeningReport openingReport) {
        List<OpeningReport> openingReports = this.openingReportMapper.selectOpeningReportByStudent(openingReport.getStudentid());
        for (OpeningReport openingReport1 : openingReports) {
            if (openingReport1.getFlag().equals(1) || openingReport1.getFlag().equals(0)) {
                return 0;
            }
        }
        openingReport.setFlag(0);
        return this.openingReportMapper.addOpeningReport(openingReport);
    }

    /**
     * 根据教师id和开题报告状态查询开题报告数量的Service层方法
     *
     * @param teacherid 教师id
     * @param flag      开题报告状态标记
     * @return 数量
     */
    @Override
    public Integer selectCountByTeacherIdAndFlag(Integer teacherid, Integer flag) {
        return this.openingReportMapper.selectCountByTeacherIdAndFlag(teacherid, flag);
    }

    /**
     * 下载开题报告的service层方法
     * @param response HttpServletResponse
     * @param id 开题报告id
     * @return 存有状态码和提示信息的集合
     */
    @Override
    public Map<String, String> downloadOpenReport(HttpServletResponse response, Integer id){
        OpeningReport openingReport = this.selectOpeningReportById(id);
        String content = openingReport.getContent() + "\n-----------------以下为教师评语-----------------\n" + openingReport.getComments();
        response.setContentType("application/force-download");
        response.setCharacterEncoding("utf-8");
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".txt";
        response.addHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName));
        Map<String, String> map= new HashMap<String, String>(2);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try{
            outputStream = response.getOutputStream();
            bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            int read = bis.read(buff);
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
