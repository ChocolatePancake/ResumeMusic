package tech.com.commoncore.avdb;

public class AVDbGlobal {
    private static AVDbGlobal avDbGlobal;

    private AVDb avDb;

    public static AVDbGlobal getInstance() {
        synchronized (AVDbGlobal.class) {
            if (avDbGlobal == null) {
                avDbGlobal = new AVDbGlobal();
            }
            return avDbGlobal;
        }
    }

    public AVDb getAVDb() {
        if (avDb == null) {
            avDb = new AVDbImpl();
        }
        return avDb;
    }
}
