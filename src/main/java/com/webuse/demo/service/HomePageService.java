package com.webuse.demo.service;

import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.HomePage;
import com.webuse.demo.result.HomePageResult;

import java.util.List;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 14:06 2019/8/20
 * @Modefied By:
 */
public interface HomePageService {

    /**
     * 添加页面信息
     */
    DemoResponse<Integer> addHomePageInfo(HomePage homePage);

    /**
     * 查询主页面
     */
    DemoResponse<String> getHomePage(String name);

    /**
     * 验证名称是否重复
     * @param name
     * @return
     */
    DemoResponse<String> forVerifyName(String name);

    /**
     * 修改主页面
     * @param result
     * @return
     */
    DemoResponse updateHomePage(HomePageResult result);

    /**
     * 删除主页面
     * @return
     */
    DemoResponse deleteHomePageById(Long id);

    /**
     * 查询所有主页面的名字
     * @return
     */
    DemoResponse<List<HomePageResult>> getAllHomePageName();

    /**
     * 通过id获取页面
     * @param id
     * @return
     */
    DemoResponse<HomePageResult> getHomePageById(Long id);
}
