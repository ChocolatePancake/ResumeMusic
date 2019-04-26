package tech.com.commoncore.event;

import com.vise.xsnow.event.IEvent;

/**
 * Time:2019/2/15
 * Desc:The ladder is real, only the climb is all.
 */
public class SwitchEvent implements IEvent {

    public int position;

    public SwitchEvent(int position) {
        this.position = position;
    }

    public SwitchEvent() {
    }
}
