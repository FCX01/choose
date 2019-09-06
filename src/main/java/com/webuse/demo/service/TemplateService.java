package com.webuse.demo.service;

import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.Template;

import java.util.List;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 16:59 2019/8/14
 * @Modefied By:
 */
public interface TemplateService {

    /**
     * 添加模板
     * @return
     */
    DemoResponse<Integer> addTemplate(Template template);

    /**
     * 查询模板
     * @return
     */
    DemoResponse<String> getTemplate(String name);

    /**
     * 查询所有模板
     * @return
     */
    DemoResponse<List<String>> getAllTemplate();

    /**
     * 更新模板(未写)
     * @return
     */
    DemoResponse<String> updateTemplate();

    /**
     * 删除模板
     * @return
     */
    DemoResponse deleteTemplateById(Long id);

    /**
     * 验证名称是否重复
     * @param name
     * @return
     */
    DemoResponse forVerifyName(String name);
}
