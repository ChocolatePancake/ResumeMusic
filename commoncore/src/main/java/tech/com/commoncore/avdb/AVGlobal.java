package tech.com.commoncore.avdb;

public class AVGlobal {
    private static AVGlobal avGlobal;

    private AVDb avDb;

    public static AVGlobal getInstance() {
        synchronized (AVGlobal.class) {
            if (avGlobal == null) {
                avGlobal = new AVGlobal();
            }
            return avGlobal;
        }
    }

    public AVDb getAVImpl() {
        if (avDb == null) {
            avDb = new AVDbImpl();
        }
        return avDb;
    }
}
