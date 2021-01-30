package cn.zcbigdata.mybits_demo.service.Impl;

import cn.zcbigdata.mybits_demo.entity.MidtermReview;
import cn.zcbigdata.mybits_demo.mapper.MidtermReviewMapper;
import cn.zcbigdata.mybits_demo.service.IMidtermReviewService;
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

@Service
public class IMidtermReviewServiceImpl implements IMidtermReviewService {

    private static final Logger logger = Logger.getLogger(IMidtermReviewServiceImpl.class);
    @Resource
    private MidtermReviewMapper midtermReviewMapper;

    @Override
    public List<MidtermReview> selectMidtermByTeacherId(int teacherid, int flag, int page, int limit) {
        int pageIndex = (page - 1) * limit;
        return this.midtermReviewMapper.selectMidtermByTeacherId(teacherid, flag, pageIndex, limit);
    }

    @Override
    public int selectMidtermCountByTeacherId(int teacherid, int flag) {
        return this.midtermReviewMapper.selectMidtermCountByTeacherId(teacherid, flag);
    }

    @Override
    public List<MidtermReview> selectMidtermByStudentId(int studentid) {
        return this.midtermReviewMapper.selectMidtermByStudentId(studentid);
    }

    @Override
    public int checkMidterm(MidtermReview midtermReview) {
        return this.midtermReviewMapper.checkMidterm(midtermReview);
    }

    @Override
    public int addComments(MidtermReview midtermReview) {
        return this.midtermReviewMapper.addComments(midtermReview);
    }

    @Override
    public int selectMidtermCountByStudentId(int studentid) {
        return this.midtermReviewMapper.selectMidtermCountByStudentId(studentid);
    }

    @Override
    public int selectMidtermCountByStuId(int studentid) {
        return this.midtermReviewMapper.selectMidtermCountByStuId(studentid);
    }

    @Override
    public int addMidterm(MidtermReview midtermReview) {
        return this.midtermReviewMapper.addMidterm(midtermReview);
    }

    @Override
    public List<MidtermReview> selectMidtermById(int id) {
        return this.midtermReviewMapper.selectMidtermById(id);
    }

    @Override
    public int deleteMidterm(int id) {
        return this.midtermReviewMapper.deleteMidterm(id);
    }

    /**
     * 下载开题报告的service层方法
     *
     * @param response HttpServletResponse
     * @param id       开题报告id
     * @return 存有状态码和提示信息的集合
     */
    @Override
    public Map<String, String> downloadMidterm(HttpServletResponse response, Integer id) {
        List<MidtermReview> midterms = this.selectMidtermById(id);
        MidtermReview midterm = null;
        for (MidtermReview midterm1 : midterms) {
            midterm = midterm1;
        }

        //拼接内容和教师评语（评分）
        String content = midterm.getContent() + "\n-----------------以下为教师评语-----------------\n" + midterm.getComments();
        //生成随机的文件名
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".txt";
        //设置response参数
        response.setContentType("application/force-download");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName));
        //存放返回值的Map
        Map<String, String> map = new HashMap<String, String>(2);
        //声明缓冲流，输出流等
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        try {
            //获取response的输出流
            outputStream = response.getOutputStream();
            //设置缓冲流
            bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            int read = bis.read(buff);
            //循环读取并输出
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
            map.put("code", "0000");
            map.put("msg", "下载成功");
        } catch (Exception e) {
            logger.error(e);
            map.put("code", "9999");
            map.put("msg", "下载失败");
        } finally {
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
