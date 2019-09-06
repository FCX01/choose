package com.webuse.demo.result;

import lombok.Data;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 19:02 2019/8/20
 * @Modefied By:
 */

@Data
public class HomePageResult {

    /**
     * 主页id
     */
    private Long id;

    /**
     * 主页名称
     */
    private String name;

    /**
     * 主页内容
     */
    private String content;

    /**
     * 是否删除
     */
    private Integer delFlag;

}
