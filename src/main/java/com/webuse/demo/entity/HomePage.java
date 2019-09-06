package com.webuse.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 11:16 2019/8/20
 * @Modefied By:
 */

@Data
@Table(name = "home_page")
public class HomePage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    /**
     * 主页名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 主页内容
     */
    @Column(name = "content")
    private String content;


    /**
     * 是否删除
     */
    @Column(name = "del_flag")
    private Integer delFlag;

}
