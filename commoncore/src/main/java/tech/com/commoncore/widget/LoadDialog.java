package tech.com.commoncore.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.TextView;

import com.aries.ui.util.FindViewUtil;

import java.lang.ref.WeakReference;

import tech.com.commoncore.R;
import tech.com.commoncore.utils.StackUtil;

/**
 * @Author: AriesHoo on 2018/7/23 14:38
 * @E-Mail: AriesHoo@126.com
 * Function: 快速创建网络请求loading
 * Description:
 * 1、2018-7-30 10:08:30 将默认Dialog变更为ProgressDialog及新增构造方式
 * 2、2018-8-23 11:19:29 修改{@link #setMessage(CharSequence)}实现方式
 */
public class LoadDialog {

    private Dialog mDialog = null;

    private Activity mActivity;
    private final WeakReference<Activity> mReference;

    public LoadDialog() {
        this(StackUtil.getInstance().getCurrent());
    }

    public LoadDialog(Activity activity) {
        this(activity, new ProgressDialog.Builder(activity)
                .setMessage(R.string.loading)
                .create());
    }

    public LoadDialog(Activity activity, Dialog dialog) {
        this.mReference = new WeakReference<>(activity);
        this.mDialog = dialog;
    }

    /**
     * 设置是否可点击返回键关闭dialog
     *
     * @param enable
     * @return
     */
    public LoadDialog setCancelable(boolean enable) {
        if (mDialog != null) {
            mDialog.setCancelable(enable);
        }
        return this;
    }

    /**
     * 设置是否可点击dialog以外关闭
     *
     * @param enable
     * @return
     */
    public LoadDialog setCanceledOnTouchOutside(boolean enable) {
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(enable);
        }
        return this;
    }

    /**
     * @param msg
     * @return
     */
    public LoadDialog setMessage(CharSequence msg) {
        if (mDialog == null) {
            return this;
        }
        if (mDialog instanceof ProgressDialog) {
            ((ProgressDialog) mDialog).setMessage(msg);
        } else {
            TextView textView = FindViewUtil.getTargetView(mDialog.getWindow().getDecorView(), TextView.class);
            if (textView != null) {
                textView.setText(msg);
            }
        }
        return this;
    }

    /**
     * @param msg
     * @return
     */
    public LoadDialog setMessage(int msg) {
        mActivity = mReference.get();
        if (mActivity != null) {
            return setMessage(mActivity.getText(msg));
        }
        return this;
    }

    /**
     * @param enable 设置全透明
     * @return
     */
    public LoadDialog setFullTrans(boolean enable) {
        if (mDialog != null) {
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            // 黑暗度
            lp.dimAmount = enable ? 0f : 0.5f;
            mDialog.getWindow().setAttributes(lp);
        }
        return this;
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public void show() {
        mActivity = mReference.get();
        if (mActivity != null && mDialog != null && !mActivity.isFinishing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        mActivity = mReference.get();
        if (mDialog != null && !mActivity.isFinishing()) {
            mDialog.dismiss();
        }
    }
}
