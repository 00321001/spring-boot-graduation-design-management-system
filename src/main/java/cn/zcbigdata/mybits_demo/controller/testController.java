package cn.zcbigdata.mybits_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping(value = "test")
    public String test() {
        return "studentFinal";
    }
}
