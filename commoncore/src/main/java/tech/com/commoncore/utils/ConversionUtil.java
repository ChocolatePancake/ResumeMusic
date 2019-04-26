package tech.com.commoncore.utils;

/**
 * Anthor:YuYiMing
 * Time:2019/3/7
 * Desc:
 */
public class ConversionUtil {

    public static final int CHINA = 0;
    public static final int AMERICAN = 1;

    /**
     * LME铜铝进口成本计算
     *
     * @param lme                LME三月铜铝
     * @param shengtieshui3month 现货三个月升贴水（美元）
     * @param shengtieshui       升贴水
     * @param exchangeRate       汇率
     * @param tariff             关税税率
     * @param vatRate            增殖税率
     * @param otherFee           其他费用
     */
    public static Double LMEPrice(Double lme, Double shengtieshui3month, Double shengtieshui, Double exchangeRate, Double tariff, Double vatRate, Double otherFee) {
        //LME进口成本 =（LME三月铜铝 –现货三个月升贴水（美元）+升贴水）*汇率*（1+关税税率）*（1+增殖税率）+其他费用
        return (lme - shengtieshui3month + shengtieshui) * exchangeRate * (1 + tariff / 100) * (1 + vatRate / 100) + otherFee;
    }

    /**
     * 进口燃料油成本计算
     *
     * @param mops         MOPS价格
     * @param shengtieshui 升贴水
     * @param exchangeRate 汇率
     * @param tariff       关税税率
     * @param vatRate      增值税税率
     * @param otherFee     其他费用
     */
    public static Double MOPSPrice(Double mops, Double shengtieshui, Double exchangeRate, Double tariff, Double vatRate, Double otherFee) {
        //进口燃料油成本＝（MOPS价格＋升贴水）×汇率×（1＋关税税率）×（1＋增值税税率）＋其他费用
        return (mops + shengtieshui) * exchangeRate * (1 + tariff / 100) * (1 + vatRate / 100) + otherFee;
    }

    /**
     * 进口大豆到岸成本 ---> 0: 中国到岸成本  1: 美国离岸成本
     *
     * @param tt1  芝加哥盘面价(美分/蒲式耳)
     * @param pam1 运费(美元/吨)
     * @param pam2 基差(美分/蒲式耳)
     * @param pam3 税率
     * @param pam4 汇率(人民币/美元)
     * @return 0: 中国到岸成本  1: 美国离岸成本
     */
    public static Double[] getSoybeanPrice(Double tt1, Double pam1, Double pam2, Double pam3, Double pam4) {
        return getPrice(tt1, pam1, pam2, pam3, pam4, 0.367437);
    }

    /**
     * 进口豆粕到岸成本 ---> 0: 中国到岸成本  1: 美国离岸成本
     *
     * @param tt1  芝加哥盘面价(美分/蒲式耳)
     * @param pam1 运费(美元/吨)
     * @param pam2 基差(美分/蒲式耳)
     * @param pam3 税率
     * @param pam4 汇率(人民币/美元)
     * @return 0: 中国到岸成本  1: 美国离岸成本
     */
    public static Double[] getSoybeannMealPrice(Double tt1, Double pam1, Double pam2, Double pam3, Double pam4) {
        return getPrice(tt1, pam1, pam2, pam3, pam4, 1.1025);
    }

    /**
     * 进口豆油到岸成本 ---> 0: 中国到岸成本  1: 美国离岸成本
     *
     * @param tt1  芝加哥盘面价(美分/蒲式耳)
     * @param pam1 运费(美元/吨)
     * @param pam2 基差(美分/蒲式耳)
     * @param pam3 税率
     * @param pam4 汇率(人民币/美元)
     * @return 0: 中国到岸成本  1: 美国离岸成本
     */
    public static Double[] getSoybeannOilPrice(Double tt1, Double pam1, Double pam2, Double pam3, Double pam4) {
        return getPrice(tt1, pam1, pam2, pam3, pam4, 22.0462);
    }

    private static Double[] getPrice(Double tt1, Double pam1, Double pam2, Double pam3, Double pam4, Double base) {
        Double[] result = new Double[2];
        result[CHINA] = ForDight(((tt1 + pam2) * base + pam1) * pam4 * pam3);
        result[AMERICAN] = ForDight((tt1 + pam2) * base);
        return result;
    }

    private static Double ForDight(Double dight) {
        return Math.round(dight * Math.pow(10, 6)) / Math.pow(10, 6);
    }

}