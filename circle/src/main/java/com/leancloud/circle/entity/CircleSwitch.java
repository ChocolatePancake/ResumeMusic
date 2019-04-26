package com.leancloud.circle.entity;

public class CircleSwitch {
    private boolean collection;
    private boolean like;
    private boolean attention;

    public CircleSwitch(boolean collection, boolean like, boolean attention) {
        this.collection = collection;
        this.like = like;
        this.attention = attention;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }
}
