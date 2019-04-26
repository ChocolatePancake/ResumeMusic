package tech.com.commoncore.futuresModel;

public class KChartData {
    public String Symbol;
    public Float c;
    public long tick;
    public String date;
    public Float o;
    public Float h;
    public Float l;
    public Float a;
    public int v;

    public KChartData() {

    }

    public KChartData(
            String Symbol,
            Float close,
            long tick,
            String date,
            Float openP,
            Float highestP,
            Float lowestP,
            Float askP,
            int v
    ) {
        this.Symbol = Symbol;
        this.c = close;
        this.tick = tick;
        this.date = date;
        this.o = openP;
        this.h = highestP;
        this.l = lowestP;
        this.a = askP;
        this.v = v;
    }
}
