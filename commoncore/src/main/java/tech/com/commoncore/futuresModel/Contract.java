package tech.com.commoncore.futuresModel;

import java.io.Serializable;

/**
 * 合约
 */
public class Contract implements Serializable {
    public String transactionId;        //合约id
    public String transactionName;      //合约名称
    public String unclear;              //不明确的字符
    public String openPrice;            //开盘价
    public String highestPrice;         //最高价
    public String lowestPrice;          //最低价
    public String yClosePrice;          //昨收价
    public String bid1Price;            //买一价
    public String askPrice;             //卖一价
    public String lastPrice;            //最新价
    public String closePrice;           //结算价
    public String yClose;               //昨结算
    public String bidVolume;            //买量
    public String askVolume;            //卖量
    public String positionVolume;       //持仓量
    public String volume;               //成交量
    public String exchangeAbb;          //交易所简称
    public String varietyAbb;           //品种简称
    public String date;                 //日期
    public Double extent;               //涨幅

    public Contract() {

    }

    public Contract(
            String transactionId,
            String transactionName,
            String unclear,
            String openPrice,
            String highestPrice,
            String lowestPrice,
            String yClosePrice,
            String bid1Price,
            String askPrice,
            String lastPrice,
            String closePrice,
            String yClose,
            String bidVolume,
            String askVolume,
            String positionVolume,
            String volume,
            String exchangeAbb,
            String varietyAbb,
            String date
    ) {
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.unclear = unclear;
        this.openPrice = openPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.yClosePrice = yClosePrice;
        this.bid1Price = bid1Price;
        this.askPrice = askPrice;
        this.lastPrice = lastPrice;
        this.closePrice = closePrice;
        this.yClose = yClose;
        this.bidVolume = bidVolume;
        this.askVolume = askVolume;
        this.positionVolume = positionVolume;
        this.volume = volume;
        this.exchangeAbb = exchangeAbb;
        this.varietyAbb = varietyAbb;
        this.date = date;
        this.extent = getExtent(yClosePrice, lastPrice);
    }

    @Override
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof Contract) {
            Contract t = (Contract) obj;
            return this.transactionName.equals(t.transactionName);
        }
        return false;
    }

    /**
     * 获取涨幅
     *
     * @param yClosePrice
     * @param closePrice
     * @return
     */
    private Double getExtent(String yClosePrice, String closePrice) {
        Double ycp = Double.valueOf(yClosePrice);
        Double cp = Double.valueOf(closePrice);
        Double ep = cp - ycp;
        Double mExtent = (ep / ycp) * 100;
        mExtent = (double) Math.round(mExtent * 100) / 100;
        return mExtent;
    }

    /**
     * 修改这个对象的属性
     *
     * @param contract
     */
    public void setContract(Contract contract) {
        if (this.transactionId.equals(contract.transactionId)) {
            this.transactionName = contract.transactionName;
            this.unclear = contract.unclear;
            this.openPrice = contract.openPrice;
            this.highestPrice = contract.highestPrice;
            this.lowestPrice = contract.lowestPrice;
            this.yClosePrice = contract.yClosePrice;
            this.bid1Price = contract.bid1Price;
            this.askPrice = contract.askPrice;
            this.lastPrice = contract.lastPrice;
            this.closePrice = contract.closePrice;
            this.yClose = contract.yClose;
            this.bidVolume = contract.bidVolume;
            this.askVolume = contract.askVolume;
            this.positionVolume = contract.positionVolume;
            this.volume = contract.volume;
            this.date = contract.date;
            this.extent = getExtent(yClosePrice, lastPrice);
        }
    }
}
