package tech.com.commoncore.futuresModel;

import android.util.Log;

import com.vise.xsnow.event.BusManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import tech.com.commoncore.utils.DataUtils;

import static tech.com.commoncore.constant.ApiConstant.BASE_URL;
import static tech.com.commoncore.constant.ApiConstant.GET_FUTURE_LIST;

public class MarketServerImpl implements MarkerServer {
    private static MarketServerImpl marketServerImpl;

    /*K线数据类型*/
    public static final String MINUTE_CHART_1 = "1";        //1分时
    public static final String MINUTE_CHART_5 = "2";        //5分K
    public static final String MINUTE_CHART_15 = "3";       //15分K
    public static final String MINUTE_CHART_30 = "4";       //30分K
    public static final String MINUTE_CHART_60 = "5";       //60分K
    public static final String MINUTE_CHART_DAY = "6";      //日K
    public static final String MINUTE_CHART_WEEK = "7";     //周K
    public static final String MINUTE_CHART_MONTH = "8";    //月K

    /*K线品种类型*/
    public static final String CHART_SHENHU = "1";          //深沪指数
    public static final String CHART_HONGKONG = "2";        //港股
    public static final String CHART_PIONEER = "3";         //创业板
    public static final String CHART_BOND = "4";            //债券
    public static final String CHART_US_SHARE = "5";        //美股
    public static final String CHART_EXCHANGE = "6";        //外汇
    public static final String CHART_FUTURES = "7";         //期货
    public static final String CHART_GOLD = "8";            //黄金

    /*根据交易所分类的合约Map*/
    private static final HashMap<String, ArrayList<Contract>> containsMap = new HashMap<>();
    /*根据合约代码请求的合约集合*/
    private static final ArrayList<Contract> optionalContracts = new ArrayList<>();
    /*交易所集合*/
    public static final String[] EXCHANGES = {"sh", "dl", "zz"};

    public static MarketServerImpl getInstance() {
        synchronized (MarketServerImpl.class) {
            if (marketServerImpl == null) {
                marketServerImpl = new MarketServerImpl();
                return marketServerImpl;
            }
            return marketServerImpl;
        }
    }

    /*==================================public function===========================================*/

    @Override
    public void requestOptionalMarket(String... abbreviations) {
        startSubscribeOptionalMarket(abbreviations);
    }

    @Override
    public void requestAllMarket() {
        startSubscribeAllMarket();
    }

    @Override
    public void requestKLinkChartData(String symbol, String lineType) {
        requestKLinkChartData(CHART_FUTURES, symbol, lineType);
    }


    @Override
    public void requestKLinkChartData(String type, String symbol, String lineType) {
        requestKLinkChartData(type, symbol, lineType, "2");
    }

    @Override
    public void requestKLinkChartData(String type, String symbol, String lineType, String isDomestic) {
        startRequestChartLineData(type, symbol, lineType, isDomestic);
    }


    @Override
    public String[] getExchange() {
        return EXCHANGES;
    }

    @Override
    public HashMap<String, ArrayList<Contract>> getContractMap() {
        return containsMap;
    }

    @Override
    public ArrayList<Contract> getContractByExchange(String exchange) {
        return containsMap.get(exchange);
    }

    /*==================================public function===========================================*/

    /*=================================private function===========================================*/

    /**
     * 请求所有行情的地方
     */
    private void startSubscribeAllMarket() {
        ViseHttp.GET(GET_FUTURE_LIST)
                .baseUrl(BASE_URL)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") != 1) {
                                BusManager.getBus().post(new FuturesEvent.MarketEventAll("数据异常"));
                                return;
                            }
                            analysisAllMarketData(jsonObject.getJSONObject("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        BusManager.getBus().post(new FuturesEvent.MarketEventAll(errMsg));
                    }
                });
    }

    /**
     * 解析并将合约信息添加到数据中去
     *
     * @param jb
     * @throws JSONException
     */
    private void analysisAllMarketData(JSONObject jb) throws JSONException {
        Iterator<String> it = jb.keys();
        while (it.hasNext()) {
            String exchange = it.next();
            JSONObject value = jb.getJSONObject(exchange);
            Iterator<String> iterator = value.keys();
            while (iterator.hasNext()) {
                String code = iterator.next();
                JSONArray ja = value.getJSONArray(code);
                Contract contract = analysisBasisMarketBuildContract(ja, code);
                addAllContractsMap(exchange, contract);
            }
        }
        BusManager.getBus().post(new FuturesEvent.MarketEventAll(containsMap));
    }

    private void startSubscribeOptionalMarket(final String... list) {
        requestOptionalMarketData(buildOptionalList(list));
    }

    private String buildOptionalList(String... abbreviations) {
        String list = "";
        for (String s : abbreviations) {
            if (list.isEmpty()) {
                list = list + s;
            } else {
                list = list + "," + s;
            }
        }
        return list;
    }

    private synchronized void requestOptionalMarketData(final String list) {
        if (list.isEmpty()) {
            return;
        }
        ViseHttp.GET(GET_FUTURE_LIST)
                .baseUrl(BASE_URL)
                .addParam("list", list)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") != 1) {
                                BusManager.getBus().post(new FuturesEvent.MarketEventOptional("数据异常"));
                                return;
                            }
                            analysisOptionalMarketData(jsonObject.getJSONObject("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        BusManager.getBus().post(new FuturesEvent.MarketEventOptional(errMsg));
                    }
                });
    }

    /**
     * 解析自选合约到自选列表里去
     *
     * @param jsonObject
     * @throws JSONException
     */
    private synchronized void analysisOptionalMarketData(JSONObject jsonObject) throws JSONException {
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String code = iterator.next();
            JSONArray ja = jsonObject.getJSONArray(code);
            Contract contract = analysisBasisMarketBuildContract(ja, code);
            addOptionalContractsList(contract);
        }
        BusManager.getBus().post(new FuturesEvent.MarketEventOptional(optionalContracts));
    }

    /**
     * 解析最基础的一个行情集合,并构建行情对象
     *
     * @param ja
     * @param code
     * @return
     */
    private Contract analysisBasisMarketBuildContract(JSONArray ja, String code) {
        try {
            Contract contract = new Contract(
                    code,
                    ja.get(0).toString(), ja.get(1).toString(), ja.get(2).toString(),
                    ja.get(3).toString(), ja.get(4).toString(), ja.get(5).toString(),
                    ja.get(6).toString(), ja.get(7).toString(), ja.get(8).toString(),
                    ja.get(9).toString(), ja.get(10).toString(), ja.get(11).toString(),
                    ja.get(12).toString(), ja.get(13).toString(), ja.get(14).toString(),
                    ja.get(15).toString(), ja.get(16).toString(), ja.get(17).toString()
            );
            return contract;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param exchange 交易所简称
     * @param contract 合约对象
     */
    private void addAllContractsMap(String exchange, Contract contract) {
        ArrayList<Contract> realContract = containsMap.get(exchange);
        if (realContract == null) {
            realContract = new ArrayList<>();
        }
        synchronized (realContract) {
            if (realContract.contains(contract)) {
                int index = realContract.indexOf(contract);
                realContract.get(index).setContract(contract);
            } else {
                realContract.add(contract);
            }
            if (!containsMap.containsKey(exchange)) {
                containsMap.put(exchange, realContract);
            }
        }
    }

    /**
     * 添加订阅的合约行情信息
     *
     * @param contract
     */
    private void addOptionalContractsList(Contract contract) {
        synchronized (optionalContracts) {
            if (optionalContracts.contains(contract)) {
                int index = optionalContracts.indexOf(contract);
                optionalContracts.get(index).setContract(contract);
            } else {
                optionalContracts.add(contract);
            }
        }
    }

    private void startRequestChartLineData(String type, String symbol, String lineType, String isDomestic) {
        ViseHttp.GET("yapi/Line/index")
                .baseUrl(BASE_URL)
                .addParam("type", type)
                .addParam("symbol", symbol)
                .addParam("line_type", lineType)
                .addParam("is_domestic", isDomestic)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("pengwang", "返回的数据是:" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            if (code != 1) {
                                BusManager.getBus().post(new FuturesEvent.ChartEvent("消息错误"));
                            }
                            analysisMiniKLineData(jsonObject.getJSONArray("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        BusManager.getBus().post(new FuturesEvent.ChartEvent(errMsg));
                    }
                });
    }

    private synchronized void analysisMiniKLineData(JSONArray jsonArray) throws JSONException {
        int count = jsonArray.length();
        ArrayList<KChartData> chartDatas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            JSONObject chartJson = jsonArray.getJSONObject(i);
            String symbol = chartJson.getString("symbol");
            Double c = chartJson.getDouble("C");
            long tick = chartJson.getLong("Tick");
            String date = chartJson.getString("D");
            Double o = chartJson.getDouble("O");
            Double h = chartJson.getDouble("H");
            Double l = chartJson.getDouble("L");
            Double a = chartJson.getDouble("A");
            int v = chartJson.getInt("V");

            KChartData chartData = new KChartData(symbol, c.floatValue(), tick, date, o.floatValue(), h.floatValue(), l.floatValue(), a.floatValue(), v);
            chartDatas.add(chartData);
        }

        BusManager.getBus().post(new FuturesEvent.ChartEvent(chartDatas));
    }
}
