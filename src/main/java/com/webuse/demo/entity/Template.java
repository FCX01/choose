package com.webuse.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 10:41 2019/8/14
 * @Modefied By:
 */
@Data
@Table(name = "template")
public class Template implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 模板内容（json字符串）
     */
    @Column(name = "content")
    private String content;

    /**
     * 模板html代码
     */
    @Column(name = "html_data")
    private String htmlData;

    /**
     * 模板图片路径
     */
    @Column(name="image_url")
    private String imageUrl;

    /**
     * 是否删除
     */
    @Column(name = "del_flag")
    private Integer delFlag;
}
