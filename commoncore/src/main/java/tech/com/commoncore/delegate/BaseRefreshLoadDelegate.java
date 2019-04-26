package tech.com.commoncore.delegate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aries.ui.util.FindViewUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import tech.com.commoncore.R;
import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.interf.IBaseRefreshLoadView;
import tech.com.commoncore.widget.FastLoadMoreView;


/**
 * @Author: AriesHoo on 2018/7/13 17:52
 * @E-Mail: AriesHoo@126.com
 * Function: 快速实现下拉刷新及上拉加载更多代理类
 * Description:
 * 1、使用StatusLayoutManager重构多状态布局功能
 * 2、2018-7-20 17:00:16 新增StatusLayoutManager 设置目标View优先级
 * 3、2018-7-20 17:44:30 新增StatusLayoutManager 点击事件处理
 */
public class BaseRefreshLoadDelegate<T> {

    public SmartRefreshLayout mRefreshLayout;
    public RecyclerView mRecyclerView;
    public BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    public StatusLayoutManager mStatusManager;
    private IBaseRefreshLoadView<T> mIBaseRefreshLoadView;
    private Context mContext;
    private FastManager mManager;
    public View mRootView;

    public BaseRefreshLoadDelegate(View rootView, IBaseRefreshLoadView<T> mIBaseRefreshLoadView) {
        this.mRootView = rootView;
        this.mIBaseRefreshLoadView = mIBaseRefreshLoadView;
        this.mContext = rootView.getContext().getApplicationContext();
        this.mManager = FastManager.getInstance();
        if (mIBaseRefreshLoadView == null) {
            return;
        }
        getRefreshLayout(rootView);
        getRecyclerView(rootView);
        initRefreshHeader();
        initRecyclerView();
        setStatusManager();
    }

    /**
     * 初始化刷新头配置
     */
    protected void initRefreshHeader() {
        if (mRefreshLayout == null) {
            return;
        }
        mRefreshLayout.setRefreshHeader(mIBaseRefreshLoadView.getRefreshHeader() != null
                ? mIBaseRefreshLoadView.getRefreshHeader() :
                mManager.getDefaultRefreshHeader() != null ?
                        mManager.getDefaultRefreshHeader().createRefreshHeader(mContext, mRefreshLayout) :
                        new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshListener(mIBaseRefreshLoadView);
        mRefreshLayout.setEnableRefresh(mIBaseRefreshLoadView.isRefreshEnable());
    }

    /**
     * 初始化RecyclerView配置
     */
    protected void initRecyclerView() {
        if (mRecyclerView == null) {
            return;
        }
        mAdapter = mIBaseRefreshLoadView.getAdapter();
        mRecyclerView.setLayoutManager(mIBaseRefreshLoadView.getLayoutManager());
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter != null) {
            setLoadMore(mIBaseRefreshLoadView.isLoadMoreEnable());
            //先判断是否Activity/Fragment设置过;再判断是否有全局设置;最后设置默认
            mAdapter.setLoadMoreView(mIBaseRefreshLoadView.getLoadMoreView() != null
                    ? mIBaseRefreshLoadView.getLoadMoreView() :
                    mManager.getLoadMoreFoot() != null ?
                            mManager.getLoadMoreFoot().createDefaultLoadMoreView(mAdapter) :
                            new FastLoadMoreView(mContext).getBuilder().build());
            if (mIBaseRefreshLoadView.isItemClickEnable()) {
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mIBaseRefreshLoadView.onItemClicked(adapter, view, position);
                    }

                });
            }
        }
    }

    public void setLoadMore(boolean enable) {
        mAdapter.setOnLoadMoreListener(enable ? mIBaseRefreshLoadView : null, mRecyclerView);
    }

    private void setStatusManager() {
        //优先使用当前配置
        View contentView = mIBaseRefreshLoadView.getMultiStatusContentView();
        if (contentView == null) {
            contentView = mRefreshLayout;
        }
        if (contentView == null) {
            contentView = mRecyclerView;
        }
        if (contentView == null) {
            return;
        }
        StatusLayoutManager.Builder builder = new StatusLayoutManager.Builder(contentView)
                .setDefaultLayoutsBackgroundColor(android.R.color.transparent)
                .setDefaultEmptyText(R.string.multi_empty)
                .setDefaultEmptyClickViewTextColor(contentView.getResources().getColor(R.color.text_tip))
                .setDefaultLoadingText(R.string.multi_loading)
                .setDefaultErrorText(R.string.multi_error)
                .setDefaultErrorClickViewTextColor(contentView.getResources().getColor(R.color.text_tip))
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        if (mIBaseRefreshLoadView.getEmptyClickListener() != null) {
                            mIBaseRefreshLoadView.getEmptyClickListener().onClick(view);
                            return;
                        }
                        mStatusManager.showLoadingLayout();
                        mIBaseRefreshLoadView.onRefresh(mRefreshLayout);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        if (mIBaseRefreshLoadView.getErrorClickListener() != null) {
                            mIBaseRefreshLoadView.getErrorClickListener().onClick(view);
                            return;
                        }
                        mStatusManager.showLoadingLayout();
                        mIBaseRefreshLoadView.onRefresh(mRefreshLayout);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        if (mIBaseRefreshLoadView.getCustomerClickListener() != null) {
                            mIBaseRefreshLoadView.getCustomerClickListener().onClick(view);
                            return;
                        }
                        mStatusManager.showLoadingLayout();
                        mIBaseRefreshLoadView.onRefresh(mRefreshLayout);
                    }
                });
        if (mManager != null && mManager.getMultiStatusView() != null) {
            mManager.getMultiStatusView().setMultiStatusView(builder, mIBaseRefreshLoadView);
        }
        mIBaseRefreshLoadView.setMultiStatusView(builder);
        mStatusManager = builder.build();
        mStatusManager.showLoadingLayout();
    }

    /**
     * 获取布局里的刷新Layout
     *
     * @param rootView
     * @return
     */
    private void getRefreshLayout(View rootView) {
        mRefreshLayout = rootView.findViewById(R.id.smartLayout_rootFastLib);
        if (mRefreshLayout == null) {
            mRefreshLayout = FindViewUtil.getTargetView(rootView, SmartRefreshLayout.class);
        }
    }

    /**
     * 获取布局RecyclerView
     *
     * @param rootView
     */
    private void getRecyclerView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_contentFastLib);
        if (mRecyclerView == null) {
            mRecyclerView = FindViewUtil.getTargetView(rootView, RecyclerView.class);
        }
    }
}
