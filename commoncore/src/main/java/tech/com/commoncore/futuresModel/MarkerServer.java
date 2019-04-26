package tech.com.commoncore.futuresModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface MarkerServer {
    /**
     * 请求自选行情
     *
     * @param abbreviations
     */
    void requestOptionalMarket(String... abbreviations);

    /**
     * 请求所有行情
     */
    void requestAllMarket();

    /**
     * 请求K线数据
     */
    void requestKLinkChartData(String symbol, String lineType);

    void requestKLinkChartData(String type, String symbol, String lineType);

    void requestKLinkChartData(String type, String symbol, String lineType, String isDomestic);

    /**
     * 获取交易所集合
     *
     * @return
     */
    String[] getExchange();

    /**
     * 获取行情map
     *
     * @return
     */
    HashMap<String, ArrayList<Contract>> getContractMap();

    /**
     * 根据交易所获取合约集合
     *
     * @param exchange
     * @return
     */
    ArrayList<Contract> getContractByExchange(String exchange);
}
