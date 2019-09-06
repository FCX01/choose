package com.webuse.demo.mapper;

import com.webuse.demo.entity.HomePage;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 14:05 2019/8/20
 * @Modefied By:
 */
public interface HomePageMapper extends Mapper<HomePage> {
    List<HomePage> getAllHomePageName();
}
