package tech.com.commoncore.event;

import com.vise.xsnow.event.IEvent;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 *
 * 登录状态改变  通知
 */
public class LoginChangeEvent implements IEvent{
    public boolean islogin;
    public LoginChangeEvent(boolean islogin) {
        this.islogin = islogin;
    }


}
