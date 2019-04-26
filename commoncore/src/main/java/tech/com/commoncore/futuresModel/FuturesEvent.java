package tech.com.commoncore.futuresModel;

import com.vise.xsnow.event.IEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class FuturesEvent {

    private static class BaseEvent<T> {
        public int code = 0;
        public String errMsg;
        public T date;

        public BaseEvent(String errMsg) {
            this.errMsg = errMsg;
        }

        public BaseEvent(T t) {
            this.code = 1;
            this.date = t;
        }
    }

    public static class MarketEventAll extends BaseEvent<HashMap<String, ArrayList<Contract>>> implements IEvent {

        public MarketEventAll(String errMsg) {
            super(errMsg);
        }

        public MarketEventAll(HashMap<String, ArrayList<Contract>> contractMap) {
            super(contractMap);
        }
    }

    public static class MarketEventOptional extends BaseEvent<ArrayList<Contract>> implements IEvent {

        public MarketEventOptional(String errMsg) {
            super(errMsg);
        }

        public MarketEventOptional(ArrayList<Contract> contracts) {
            super(contracts);
        }
    }

    public static class ChartEvent extends BaseEvent<ArrayList<KChartData>> implements IEvent {

        public ChartEvent(String errMsg) {
            super(errMsg);
        }

        public ChartEvent(ArrayList<KChartData> kChartData) {
            super(kChartData);
        }
    }

}
