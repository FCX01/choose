package com.webuse.demo.mapper;

import com.webuse.demo.entity.Template;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 16:48 2019/8/14
 * @Modefied By:
 */
public interface TemplateMapper extends Mapper<Template> {

    String findContentByName(@Param("name") String name);
}
