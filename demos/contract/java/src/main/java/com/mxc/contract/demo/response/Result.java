package com.mxc.contract.demo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用的返回结果
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -3082141838299455573L;
    private boolean success;
    private Integer code;
    private String message;
    /**
     * 存放执行或查询之后的结果
     */
    private T data;

    public Result() {

    }

}