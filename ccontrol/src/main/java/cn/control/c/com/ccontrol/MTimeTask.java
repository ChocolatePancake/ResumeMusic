package cn.control.c.com.ccontrol;

import java.util.Timer;
import java.util.TimerTask;

public class MTimeTask {

    /*时间间隔*/
    private long timeInterval = 1000;

    private boolean openState = true;

    private TimerRun timerRun;

    private Timer timer;

    private TimerTask timerTask;

    public MTimeTask(int interval, TimerRun timerRun) {
        this.timeInterval = interval;
        this.timerRun = timerRun;
        timerRun.run();
        startTimeInterval();
    }

    /*======================================public function==-====================================*/

    /**
     * 开始定时器
     */
    public void startTask() {
        startTimer();
    }

    public void stopTask() {
        cancel();
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    /*======================================public function==-====================================*/


    /*======================================public function==-====================================*/
    private void startTimer() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timerRun.run();
                }
            };
            timer.schedule(timerTask, 0, timeInterval);
        }
    }

    private void startTimeInterval() {
        startTimer();
    }


    public abstract static class TimerRun {
        public abstract void run();
    }
}
