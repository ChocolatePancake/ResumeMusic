package com.leancloud.home.entity;

import java.io.Serializable;

/**
 * Desc:深沪指数
 */
public class RecommendFuturesEntity implements Serializable{

    /**
     * volume : 4315824
     * amount : 38664633
     * pricechange : -18.8378
     * name : 上证指数
     * changepercent : -0.61
     * trade : 3083.2613
     */

    private String volume;
    private String amount;
    private String pricechange;
    private String name;
    private String changepercent;
    private String trade;

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPricechange() {
        return pricechange;
    }

    public void setPricechange(String pricechange) {
        this.pricechange = pricechange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(String changepercent) {
        this.changepercent = changepercent;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
}
