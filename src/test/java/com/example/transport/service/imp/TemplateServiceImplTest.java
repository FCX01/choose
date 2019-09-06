package com.example.transport.service.imp;

import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.Template;
import com.webuse.demo.service.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 17:56 2019/8/14
 * @Modefied By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateServiceImplTest {

    @Autowired
    TemplateService templateService;


    @Test
    public void addTemplate() {
        Template template = new Template();
        template.setName("template1");
        template.setContent("{ \"title\": \"大标题\", \"synopsis\": \"大标题简介\", \"littletitle\": \"小标题\"}");
        template.setDelFlag(0);
        DemoResponse<Integer> response = templateService.addTemplate(template);
        System.out.println(response);
    }

    @Test
    public void getTemplate() {
        DemoResponse<String> str = templateService.getTemplate("测试3");
        System.out.println(str);
    }

    @Test
    public void getAllTemplate() {
        DemoResponse<List<String>> allTemplate = templateService.getAllTemplate();
        System.out.println(allTemplate);
    }

}