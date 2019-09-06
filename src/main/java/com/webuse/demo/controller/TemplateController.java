package com.webuse.demo.controller;

import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.Template;
import com.webuse.demo.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 18:35 2019/8/14
 * @Modefied By:
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @RequestMapping("/addTemplate")
    public DemoResponse<Integer> addTemplate(Template template){
        System.out.println("添加执行成功");
        return templateService.addTemplate(template);
    }

    @RequestMapping("/getTemplate")
    public DemoResponse<String> getTemplate(@RequestBody String name) {
        DemoResponse<String> template = templateService.getTemplate(name);
        return template;
    }

    @RequestMapping("/forVerifyName")
    public DemoResponse forVerifyName(String name) {
        DemoResponse verifyName = templateService.forVerifyName(name);
        return verifyName;
    }

    @RequestMapping("/getAllTemplate")
    public DemoResponse<List<String>> getAllTemplate() {
        DemoResponse<List<String>> allTemplate = templateService.getAllTemplate();
        return allTemplate;
    }

    @RequestMapping("/deleteTemplateById")
    public DemoResponse deleteTemplateById(Long id) {
        return templateService.deleteTemplateById(id);
    }
}
