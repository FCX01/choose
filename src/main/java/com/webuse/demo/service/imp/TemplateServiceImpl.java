package com.webuse.demo.service.imp;

import com.alibaba.fastjson.JSON;
import com.webuse.demo.common.DemoCode;
import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.Template;
import com.webuse.demo.mapper.TemplateMapper;
import com.webuse.demo.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 17:09 2019/8/14
 * @Modefied By:
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    TemplateMapper templateMapper;

    @Override
    public DemoResponse<Integer> addTemplate(Template template) {
        if (Objects.isNull(template.getName())) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Example example = new Example(Template.class);
        example.createCriteria().andEqualTo("name", template.getName()).andEqualTo("delFlag", 0);
        Template tp = templateMapper.selectOneByExample(example);
        if (Objects.nonNull(tp)) {
            return new DemoResponse(false,DemoCode.DC107.getCode(),DemoCode.DC107.getMessage(),null);
        }
        template.setDelFlag(0);
        templateMapper.insert(template);
        return new DemoResponse(true,DemoCode.DC105.getCode(),DemoCode.DC105.getMessage(),null);
    }

    @Override
    public DemoResponse<String> getTemplate(String name) {
        if (Objects.isNull(name)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Example example = new Example(Template.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("delFlag", 0);
        Template template = templateMapper.selectOneByExample(example);
        if (Objects.isNull(template)) {
            return  new DemoResponse(false,DemoCode.DC102.getCode(),DemoCode.DC102.getMessage(),null);
        }
        LinkedHashMap<Object,Object> map = new LinkedHashMap<>();
        map.put("id", template.getId());
        map.put("name", template.getName());
        LinkedHashMap hashMap = JSON.parseObject(template.getContent(), LinkedHashMap.class);
        for (Object key : hashMap.keySet()) {
            map.put(key,hashMap.get(key));
        }
        map.put("imageUrl", template.getImageUrl());
        String jsonString = JSON.toJSONString(map);
        return DemoResponse.success(jsonString);
    }

    @Override
    public DemoResponse<List<String>> getAllTemplate() {
        Template template = new Template();
        template.setDelFlag(0);
        Example example = new Example(Template.class);
        example.createCriteria().andEqualTo("delFlag",0);
        List<Template> templates = templateMapper.selectByExample(example);
        if (Objects.isNull(templates)) {
            return  new DemoResponse(false,DemoCode.DC102.getCode(),DemoCode.DC102.getMessage(),null);
        }
        List<String> list = new ArrayList<>();
        for (Template template1 : templates) {
            LinkedHashMap<Object,Object> map = new LinkedHashMap<>();
            map.put("id", template1.getId());
            map.put("name", template1.getName());
            LinkedHashMap hashMap = JSON.parseObject(template1.getContent(), LinkedHashMap.class);
            for (Object key : hashMap.keySet()) {
                map.put(key,hashMap.get(key));
            }
            map.put("imageUrl", template1.getImageUrl());

            String jsonString = JSON.toJSONString(map);
            list.add(jsonString);
        }
        return new DemoResponse(false,DemoCode.DC105.getCode(),DemoCode.DC105.getMessage(),list);
    }

    @Override
    public DemoResponse<String> updateTemplate() {
        return null;
    }

    @Override
    public DemoResponse deleteTemplateById(Long id) {
        if (Objects.isNull(id)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Template tp = templateMapper.selectOneByExample(id);
        if (Objects.isNull(tp)) {
            return new DemoResponse(false,DemoCode.DC102.getCode(), DemoCode.DC102.getMessage(), null);
        }
        Template template = new Template();
        template.setId(id);
        template.setDelFlag(1);
        int update = templateMapper.updateByPrimaryKeySelective(template);
        if (update == 0) {
            return DemoResponse.fail("删除失败");
        }
        return DemoResponse.success("删除成功");
    }

    @Override
    public DemoResponse forVerifyName(String name) {
        if (Objects.isNull(name)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Example example = new Example(Template.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("delFlag", 0);
        Template template = templateMapper.selectOneByExample(example);
        if (Objects.nonNull(template)) {
            return  new DemoResponse(false,DemoCode.DC107.getCode(),DemoCode.DC107.getMessage(),null);
        }
        return DemoResponse.success("可以使用该名称");
    }
}
