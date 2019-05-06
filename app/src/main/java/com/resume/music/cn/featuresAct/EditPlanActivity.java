package com.resume.music.cn.featuresAct;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.resume.music.cn.R;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.STATUS_TYPE_ING;
import static tech.com.commoncore.manager.ModelPathManager.main_editPlan;

@Route(path = main_editPlan)
public class EditPlanActivity extends BaseTitleActivity implements View.OnClickListener {

    private EditText titleEt, contentEt;
    private TextView startTx, endTx;

    private DatePickerDialog datePickerDialog;

    private String startTime = "";
    private String endTime = "";

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("编辑策划任务")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_edit_plan;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleEt = findViewById(R.id.edit_plan_title);
        contentEt = findViewById(R.id.edit_plan_content);
        startTx = findViewById(R.id.edit_plan_start_time);
        endTx = findViewById(R.id.edit_plan_end_time);

        findViewById(R.id.edit_plan_submit).setOnClickListener(this);
        startTx.setOnClickListener(this);
        endTx.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_plan_start_time:
                handlerStartTimeSelect();
                break;
            case R.id.edit_plan_end_time:
                handlerEndTimeSelect();
                break;
            case R.id.edit_plan_submit:
                handlerSubmitParty();
                break;
        }
    }

    private void handlerSubmitParty() {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();

        if (title.isEmpty()) {
            ToastUtil.show("主题不能为空");
            return;
        }

        if (content.isEmpty()) {
            ToastUtil.show("内容不能为空");
            return;
        }

        if (startTime.isEmpty()) {
            ToastUtil.show("请选择开始时间");
            return;
        }

        if (startTime.isEmpty()) {
            ToastUtil.show("请选择结束时间");
            return;
        }

        showLoading();
        AVGlobal.getInstance().getAVImpl().addPlan(title, content, startTime, endTime, STATUS_TYPE_ING, new SaveCallback() {
            @Override
            public void done(AVException e) {
                hideLoading();
                if (e == null) {
                    ToastUtil.show("发布成功");
                } else {
                    ToastUtil.show("发布失败,清检测网络");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handlerStartTimeSelect() {
        datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startTime = year + ":" + month + ":" + dayOfMonth;
                startTx.setText("开始时间 :" + startTime);
            }
        });
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handlerEndTimeSelect() {
        datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endTime = year + ":" + month + ":" + dayOfMonth;
                endTx.setText("结束时间 :" + startTime);
            }
        });
        datePickerDialog.show();
    }
}
