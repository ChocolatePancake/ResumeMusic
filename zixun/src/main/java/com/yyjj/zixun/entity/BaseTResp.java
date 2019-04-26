package com.yyjj.zixun.entity;

/**
 * Time:2018/11/8
 * Desc: 数据加载的基类. 包含数据请求的最外层  字段: code、msg、data
 */
public class BaseTResp<T> {
        public int code;
        public String msg;
        public T data;

        public BaseTResp(int code, String msg, T data) {
                this.code = code;
                this.msg = msg;
                this.data = data;
        }
}
