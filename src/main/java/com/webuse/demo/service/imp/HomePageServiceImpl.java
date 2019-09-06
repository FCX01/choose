package com.webuse.demo.service.imp;

import com.alibaba.fastjson.JSON;
import com.webuse.demo.common.DemoCode;
import com.webuse.demo.common.DemoResponse;
import com.webuse.demo.entity.HomePage;
import com.webuse.demo.mapper.HomePageMapper;
import com.webuse.demo.mapper.TemplateMapper;
import com.webuse.demo.result.HomePageResult;
import com.webuse.demo.service.HomePageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 14:07 2019/8/20
 * @Modefied By:
 */
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    HomePageMapper homePageMapper;

    @Autowired
    TemplateMapper templateMapper;

    @Override
    public DemoResponse<Integer> addHomePageInfo(HomePage homePage) {
        if (Objects.isNull(homePage.getName()) || Objects.isNull(homePage.getContent())){
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Example example = new Example(HomePage.class);
        example.createCriteria().andEqualTo("name", homePage.getName()).andEqualTo("delFlag", 0);
        HomePage hp = homePageMapper.selectOneByExample(example);
        if (Objects.nonNull(hp)) {
            return new DemoResponse(false,DemoCode.DC107.getCode(),DemoCode.DC107.getMessage(),null);
        }
        homePage.setDelFlag(0);
        homePageMapper.insert(homePage);
        return new DemoResponse(true,DemoCode.DC105.getCode(),DemoCode.DC105.getMessage(),null);
    }

    @Override
    public DemoResponse<String> getHomePage(String name) {
        if (Objects.isNull(name)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Example example = new Example(HomePage.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("delFlag", 0);
        HomePage homePage = homePageMapper.selectOneByExample(example);
        if (Objects.isNull(homePage)) {
            return  new DemoResponse(false,DemoCode.DC102.getCode(),DemoCode.DC102.getMessage(),null);
        }
        String result = "";
        //获取数据库中content的值，然后JSON.parseObject(homePage.getContent(), LinkedHashMap.class)将其以key-value对存入链式Map中
        LinkedHashMap hashMap = JSON.parseObject(homePage.getContent(), LinkedHashMap.class);
        //第一次遍历Map
        for (Object key : hashMap.keySet()) {
            System.out.println(key);
            System.out.println(hashMap.get(key));
            //通过第一次遍历后，将每个value再次以key-value对存入链式Map中
            LinkedHashMap hashMap1 = JSON.parseObject(hashMap.get(key).toString(), LinkedHashMap.class);
            if (hashMap1.get("name") == null || hashMap1.get("name") == "") {
                return new DemoResponse(false,DemoCode.DC106.getCode(),DemoCode.DC106.getMessage(),null);
            }
            //如果key == "name" 则取出模板表中的html代码
            String contentByName = templateMapper.findContentByName(hashMap1.get("name").toString());
            //用于替换
            for (Object object : hashMap1.keySet()) {
                if ("id".equals(object) || "name".equals(object)) {
                    continue;
                }
                //替换html代码中正则匹配的的某些子串
                contentByName = replaceParam(contentByName, object.toString(), hashMap1.get(object).toString());
            }
            result += "\n" + contentByName;
        }
        return new DemoResponse(true,DemoCode.DC105.getCode(),DemoCode.DC105.getMessage(),result);
    }

    @Override
    public DemoResponse<String> forVerifyName(String name) {
        if (Objects.isNull(name)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        Example example = new Example(HomePage.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("delFlag", 0);
        HomePage homePage = homePageMapper.selectOneByExample(example);
        if (Objects.nonNull(homePage)) {
            return  new DemoResponse(false,DemoCode.DC107.getCode(),DemoCode.DC107.getMessage(),null);
        }
        return DemoResponse.success("可以使用该名称");
    }

    @Override
    public DemoResponse updateHomePage(HomePageResult result) {
        if (Objects.isNull(result.getName()) || Objects.isNull(result.getContent())){
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        HomePage homePage = new HomePage();
        homePage.setId(result.getId());
        homePage.setName(result.getName());
        homePage.setContent(result.getContent());
        int update = homePageMapper.updateByPrimaryKeySelective(homePage);
        if(update == 0) {
            return DemoResponse.fail("更新失败");
        }
        return DemoResponse.success("更新成功");
    }


    @Override
    public DemoResponse deleteHomePageById(Long id) {
        if (Objects.isNull(id)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        HomePage hp = homePageMapper.selectByPrimaryKey(id);
        if (Objects.isNull(hp)) {
            return new DemoResponse(false,DemoCode.DC102.getCode(), DemoCode.DC102.getMessage(), null);
        }
        HomePage homePage = new HomePage();
        homePage.setId(id);
        homePage.setDelFlag(1);
        int update = homePageMapper.updateByPrimaryKeySelective(homePage);
        if (update == 0) {
            return DemoResponse.fail("删除失败");
        }
        return DemoResponse.success("删除成功");
    }

    @Override
    public DemoResponse<List<HomePageResult>> getAllHomePageName() {
        List<HomePageResult> list = new ArrayList<>();
        List<HomePage> allHomePageName = homePageMapper.getAllHomePageName();
        if (Objects.isNull(allHomePageName)) {
            return new DemoResponse(false,DemoCode.DC102.getCode(),DemoCode.DC102.getMessage(),null);
        }
        for (HomePage homePage : allHomePageName) {
            HomePageResult result = new HomePageResult();
            System.out.println(homePage);
            BeanUtils.copyProperties(homePage,result);
            list.add(result);
        }
        return DemoResponse.success(list);
    }

    @Override
    public DemoResponse<HomePageResult> getHomePageById(Long id) {
        if (Objects.isNull(id)) {
            return new DemoResponse(false,DemoCode.DC101.getCode(),DemoCode.DC101.getMessage(),null);
        }
        HomePage homePage = homePageMapper.selectByPrimaryKey(id);
        if (Objects.isNull(homePage)) {
            return new DemoResponse(false, DemoCode.DC102.getCode(), DemoCode.DC102.getMessage(),null);
        }
        HomePageResult result = new HomePageResult();
        BeanUtils.copyProperties(homePage,result);
        String string = JSON.toJSONString(result);
        return DemoResponse.success(string);
    }

    /**
     * 替换目标字符串target里以 "\\$\\{"+ key +"\\}" 匹配的子串,根据key的值动态改变正则表达式
     * @param target
     * @param key
     * @param value
     * @return
     */
    public String replaceParam(String target,String key,String value) {
        Pattern pattern = Pattern.compile("\\$\\{" + key + "\\}");
        Matcher matcher = pattern.matcher(target);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
