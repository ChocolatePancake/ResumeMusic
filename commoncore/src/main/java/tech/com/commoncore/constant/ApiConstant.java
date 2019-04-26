package tech.com.commoncore.constant;

/**
 * Anthor:HeChuan
 * Time:2018/11/5
 * Desc:
 */
public class ApiConstant {

    //host-->
    public static final String BASE_URL_OLD = "http://data.yingju8.com/";
    public static final String BASE_URL = "http://data.fk7h.com/";


    //资讯类-分类资讯列表  无分页  https://data.yingju8.com/yapi/article/alist/?cid=123&limit=100
    public static final String ARTICLE_LIST = "/yapi/article/alist";
    //资讯类-分类资讯列表  有分页
    //http://data.yingju8.com/yapi/article/alistpage/?category=18&page=1  http://data.fk7h.com/yapi/article/alist/?cid=25&limit=10
    public static final String ARTICLE_LIST_PAGE = "yapi/article/alistpage";

    //期货所有的类别数据.
    public static final String ALL_CATEGORY = "yapi/exchange/category_all";

    //财经日历搜索 ,参数 ?year=2018&monthday=0903  http://data.fk7h.com/yapi/index/cjrl/?year=2018&monthday=1128
    public static final String CJRL = "yapi/index/cjrl";

    //获取期货列表,国内期货列表
//    http://data.yingju8.com/yapi/exchange/lists?type=sh&list=SC0,HC0
    public static final String GET_FUTURE_LIST = "yapi/exchange/lists";

    //国际期货数据分类.
//    http://data.yingju8.com/yapi/Foreign/futures_list ? symbol=BP,CD&&type=1 (这里的类型不能同时存在多个 ，只能是单个类型,1 外汇期货 2贵金属 3工业品 4农产品 5能源 6全球股指期货)
    public static final String GET_FOREIGN_FUTURE_LIST = "yapi/Foreign/futures_list";

    //期货详情,WEBVIEW页面  + ?symbol
    public static final String GET_FUTURE_DETAIL = "http://stock.yingju8.com/home/charts";

    //获取财经事件(快讯)
    public static final String GET_CJ_EVENT = "yapi/index/cjevent";

    //日 k线图
    public static final String DATE_K_LINE = "yapi/exchange/date_k_line";
    //分时 k线图
    public static final String MINI_K_LINE = "yapi/exchange/mini_k_line";
    //5 15 30 60分钟 k线图
    public static final String NUM_K_LINE = "yapi/exchange/num_k_line";
    //幻灯片
    public static final String INDEX = "yapi/slide/index";

    //视频 http://stock.yingju8.com/video/%@.mp4
    public static final String VIDEO = "video/";

    //---------------------------------------股票---------------------------------
    public static final String BASE_URL_JUHE = "http://web.juhe.cn:8080/";
    public static final String BASE_PARAM_JUHE_KAY = "184f2b9b36517079d8e0efc61c1b4fe8";
    public static final String BASE_URL_FH7H = "http://data.fk7h.com/";

    //股票 http://data.fk7h.com/yapi/Foreign/stock_overview?=&=
    public static final String URL_UP_DOWM_FLAT = "/yapi/Foreign/stock_overview";

    // 沪股票涨跌幅
    // http://data.yingju8.com/yapi/stock/getsinacf?page=1&num=10&asc=0&node=sh_a   asc=0涨幅1跌幅
    //  http://web.juhe.cn:8080/finance/stock/shall?stock=a&page=1&type=1&key=184f2b9b36517079d8e0efc61c1b4fe8
    public static final String GET_PRICE_LIMIT_SHENHU = "finance/stock/shall";
    //深沪板块 http://data.fk7h.com/yapi/Foreign/exponent
    public static final String GET_PRICE_LIMIT_SHANGHAI_ZHISHU = "yapi/Foreign/exponent";

    //美股涨跌幅http://web.juhe.cn:8080/ page=&type=3&key=184f2b9b36517079d8e0efc61c1b4fe8   1(20条默认),2(40条),3(60条),4(80条)
    public static final String GET_PRICE_LIMIT_AMERICA = "finance/stock/usaall";
    //美股板块http://data.fk7h.com/yapi/exchange/us_exponent
    public static final String GET_PRICE_LIMIT_AMERICA_ZHISHU = "yapi/exchange/us_exponent";

    //港股 涨跌幅http://web.juhe.cn:8080/  page=&type=&key=184f2b9b36517079d8e0efc61c1b4fe8   type:1(20条默认),2(40条),3(60条),4(80条)
    public static final String GET_PRICE_LIMIT_HONGKONG = "finance/stock/hkall";
    //港股 板块 http://data.fk7h.com/  ?list=hkHSI,hkHSMPI,hkCSI300
    public static final String GET_PRICE_LIMIT_HK_zhishu = "yapi/exchange/lists";

    //---------------------------------------币---------------------------------
    //比特币行情 base = BTC、 ETH、 EOS、 SOC、 BCD、 ELF、 INK、
    public static final String COIN_MARKET = "yapi/Currency/bihucj";
    //比特币快讯
    public static final String COIN_FLASH = "yapi/Dubiwang/news";


    //-------------------------自选接口-------------------
    //美股
    public static final String UUST_URL = "yapi/Bond/us_list?";
    //港股，沪深（板块内容）
//    http://data.fk7h.com/yapi/Hk/shen? page=1&asc=1&number=40&sort=symbol&type=chgn_730327&symbol=00001
    public static final String HUSHENG_URL = "yapi/Hk/shen?";
    public static final String HK_URL = "yapi/Hk/index?";

    //财经资讯 http://data.fk7h.com/yapi/news/hk_jrj_news?type=gdxw&page=1
    public static final String GET_JR_NEWS = "/yapi/news/hk_jrj_news";
    //同花顺要闻直播  https://data.fk7h.com/yapi/news_letter/jqka_news
    public static final String URL_NEWS_TONGHUSHUN = "/yapi/news_letter/jqka_news";
    //https://data.fk7h.com/yapi/news_letter/qihoo_mlist
    public static final String URL_NEWS = "/yapi/hq/qnews";
    //https://data.fk7h.com/yapi/news_letter/qihoo_mlist
    public static final String URL_NEWS_1 = "/yapi/news_letter/qihoo_mlist";
    //http://data.fk7h.com/yapi/hq/qnews?pidx=1&ps=10&types=1,5,6
    public static final String URL_FLASH = "/yapi/hq/qnews";

    // 国内期货详情  http://stock.fk7h.com/home/charts?symbol=HK00002;
    public static final String URL_INLAND_K = "http://stock.fk7h.com/home/charts";
}
