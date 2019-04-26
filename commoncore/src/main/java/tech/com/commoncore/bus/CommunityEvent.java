package tech.com.commoncore.bus;

import com.avos.avoscloud.AVObject;
import com.vise.xsnow.event.IEvent;

public class CommunityEvent implements IEvent {
    public AVObject object;

    public CommunityEvent(AVObject object) {
        this.object = object;
    }
}
