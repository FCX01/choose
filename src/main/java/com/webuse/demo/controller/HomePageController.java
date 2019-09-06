package com.webuse.demo.controller;

import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.HomePage;
import com.webuse.demo.result.HomePageResult;
import com.webuse.demo.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 14:05 2019/8/20
 * @Modefied By:
 */
@RestController
@RequestMapping("/homePage")
public class HomePageController {

    @Autowired
    HomePageService homePageService;

    @RequestMapping("/addHomePageInfo")
    public DemoResponse<Integer> addHomePageInfo( HomePage homePage) {
        return homePageService.addHomePageInfo(homePage);
    }

    @RequestMapping("/getHomePage")
    public DemoResponse<String> getHomePage(@RequestBody String name) {
        DemoResponse<String> homePage = homePageService.getHomePage(name);
        return homePage;
    }

    @RequestMapping("/forVerifyName")
    public DemoResponse<String> forVerifyName(String name) {
        DemoResponse<String> verifyName = homePageService.forVerifyName(name);
        return verifyName;
    }

    @RequestMapping("/deleteHomePageById")
    public DemoResponse deleteHomePageById(Long id) {
        DemoResponse demoResponse = homePageService.deleteHomePageById(id);
        return demoResponse;
    }

    @RequestMapping("/getAllHomePageName")
    public DemoResponse<List<HomePageResult>> getAllHomePageName() {
        DemoResponse<List<HomePageResult>> allHomePageName = homePageService.getAllHomePageName();
        return allHomePageName;
    }

    @RequestMapping("/getHomePageById")
    public DemoResponse getHomePageById(Long id) {
        DemoResponse<HomePageResult> homePageById = homePageService.getHomePageById(id);
        return homePageById;
    }

    @RequestMapping("/updateHomePage")
    public DemoResponse updateHomePage(HomePageResult result) {
        DemoResponse demoResponse = homePageService.updateHomePage(result);
        return demoResponse;
    }
}
