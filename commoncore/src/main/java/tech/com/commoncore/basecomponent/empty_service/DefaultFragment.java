package tech.com.commoncore.basecomponent.empty_service;

import android.os.Bundle;

import com.aries.ui.view.title.TitleBarView;

import tech.com.commoncore.R;
import tech.com.commoncore.base.BaseTitleFragment;

/**
 * Anthor:NiceWind
 * Time:2019/3/25
 * Desc:The ladder is real, only the climb is all.
 */
public class DefaultFragment extends BaseTitleFragment {

    public static DefaultFragment newInstance() {

        Bundle args = new Bundle();

        DefaultFragment fragment = new DefaultFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("默认fragment");
    }

    @Override
    public int getContentLayout() {
        return R.layout.layout_status_layout_manager_empty;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
