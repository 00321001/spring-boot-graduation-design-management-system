package cn.zcbigdata.mybits_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ts119
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/stu", method = RequestMethod.GET)
    public String testPage(){
        return "studentManage";
    }
}
