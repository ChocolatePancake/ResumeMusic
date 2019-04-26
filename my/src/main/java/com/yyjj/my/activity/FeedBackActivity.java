package com.yyjj.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.yyjj.my.R;
import com.yyjj.my.db.AVDbManager;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.my_feedBackPath;

/**
 * Desc:
 */
@Route(path = my_feedBackPath)
public class FeedBackActivity extends BaseTitleActivity {

    private EditText etFeedback;
    private TextView textCountTx;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("意见反馈")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.my_activity_feedback;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etFeedback = findViewById(R.id.et_feedback);
        textCountTx = findViewById(R.id.text_count_tx);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked();
            }
        });

        setTextCountTx();

        etFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTextCountTx();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setTextCountTx() {
        String idea = etFeedback.getText().toString();
        String count = String.valueOf(idea.length());
        textCountTx.setText(count + "/255");
    }

    public void onViewClicked() {
        String content = etFeedback.getText().toString().trim();
        if (DataUtils.isEmptyString(content)) {
            ToastUtil.show("亲,内容不能为空哟...");
            return;
        }
        showLoading();
        AVObject feedback = new AVObject(AVDbManager.TABLE_FEEDBACK);
        feedback.put(AVDbManager.FEEDBACK_USER, AVUser.getCurrentUser());
        feedback.put(AVDbManager.FEEDBACK_CONTENT, content);
        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                ToastUtil.show("感谢您的反馈。");
                finish();
            }
        }, 1000);

//        feedback.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                hideLoading();
//                    ToastUtil.show("感谢您的反馈。");
//                if (e == null) {
//                    etFeedback.setText("");
//                    FeedBackActivity.this.finish();
//                } else {
//                    ToastUtil.show("反馈失败,请检查网络是否正常。");
//                }
//            }
//        });
    }
}
