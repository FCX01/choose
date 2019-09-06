package com.webuse.demo.common;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 19:48 2019/8/14
 * @Modefied By:
 */
public enum DemoCode {
    DC101("DC101","请求参数不能为空"),
    DC102("DC102","查询结果为空！"),
    DC103("DC103","非法的请求参数"),
    DC104("DC104","服务异常"),
    DC105("DC105","成功"),
    DC106("DC106","数据不存在"),
    DC107("DC108","该模板名称已存在");


    private String code;
    private String message;
    DemoCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
