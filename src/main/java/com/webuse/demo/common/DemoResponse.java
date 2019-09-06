package com.webuse.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: MingYG
 * @Description:
 * @Date: Create in 19:26 2019/8/14
 * @Modefied By:
 */
@Data
public class DemoResponse<T> implements Serializable {

    /**
     * 接口返回结果
     * true : 表示成功
     * false : 表示失败
     */
    private Boolean status;

    /**
     * 返回错误编码
     * 当status = false , errorCode 不为空
     */
    private String errorCode;

    /**
     * 返回错误信息
     * 当status = false , errorMsg 不为空
     */
    private String errorMsg;

    /**
     * 返回结果
     */
    private T data;

    public DemoResponse() {
    }

    public DemoResponse(Boolean status, String errorCode, String errorMsg, T data) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static DemoResponse success(Object data) {
        DemoResponse demoResponse = new DemoResponse();
        demoResponse.setStatus(true);
        demoResponse.setErrorCode("success");
        demoResponse.setErrorMsg("操作成功");
        demoResponse.setData(data);
        return demoResponse;
    }

    public static DemoResponse success(String errorMsg) {
        DemoResponse demoResponse = new DemoResponse();
        demoResponse.setStatus(true);
        demoResponse.setErrorCode("success");
        demoResponse.setErrorMsg(errorMsg);
        demoResponse.setData(null);
        return demoResponse;
    }

    public static DemoResponse fail(String errorMsg) {
        DemoResponse demoResponse = new DemoResponse();
        demoResponse.setStatus(false);
        demoResponse.setErrorCode("fail");
        demoResponse.setErrorMsg(errorMsg);
        demoResponse.setData(null);
        return demoResponse;
    }
}
