package tech.com.commoncore.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import tech.com.commoncore.R;
import tech.com.commoncore.utils.ScreenUtils;

/**
 * Author:ChenPengBo
 * Date:2018/11/3
 * Desc:网络错误提示Dialog
 * Version:1.0
 */
public class NetErrorDialog {

    private Dialog dialog;

    private Context context;

    public NetErrorDialog(Context context) {
        this.context = context;

        View view = View.inflate(context, R.layout.dialog_net_error, null);

        dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        initView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = ScreenUtils.getScreenWidth(context);
        dialogWindow.setAttributes(lp);
    }

    private void initView(View view) {
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public Dialog getDialog() {
        return dialog;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
        dialog.dismiss();
    }

    private OnCloseListener onCloseListener;

    public interface OnCloseListener {
        void onClose();
    }

    public NetErrorDialog setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
        return this;
    }
}
