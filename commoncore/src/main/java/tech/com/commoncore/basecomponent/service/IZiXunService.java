package tech.com.commoncore.basecomponent.service;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 * 资讯的入口
 */
public interface IZiXunService {
    Fragment newArticleFragment(Bundle bundle);

    Fragment newFlashFragment(Bundle bundle);

    Fragment newCalendarFragment(Bundle bundle);

    Fragment newVideoFragment(Bundle bundle);
}
