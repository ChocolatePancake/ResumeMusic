package tech.com.commoncore.bus;

import com.avos.avoscloud.AVObject;
import com.vise.xsnow.event.IEvent;

public class CommitEvent implements IEvent {
    public AVObject object;

    public CommitEvent(AVObject object) {
        this.object = object;
    }
}
