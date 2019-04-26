package com.leancloud.home.entity;

/**
 * Desc:
 */
public class LoopBean {
    public int img;
    public String text;
    public String content;

    public LoopBean(int img, String text) {
        this.img = img;
        this.text = text;
    }
    public LoopBean(String text, String content, int img) {
        this.img = img;
        this.text = text;
        this.content = content;
    }

    public LoopBean() {
    }
}
