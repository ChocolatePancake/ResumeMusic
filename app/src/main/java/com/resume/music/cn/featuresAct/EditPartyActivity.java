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

import static tech.com.commoncore.manager.ModelPathManager.main_editParty;

@Route(path = main_editParty)
public class EditPartyActivity extends BaseTitleActivity implements View.OnClickListener {
    private EditText titleEt, contentEt, peopleEt, addressEt;
    private TextView startTx, endTx;
    private Button subButton;

    private DatePickerDialog datePickerDialog;


    private long startTime, endTime;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("编辑活动")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_edit_party;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleEt = findViewById(R.id.edit_party_title);
        contentEt = findViewById(R.id.edit_party_content);
        peopleEt = findViewById(R.id.edit_party_people);
        addressEt = findViewById(R.id.edit_party_address);
        startTx = findViewById(R.id.edit_party_start_time);
        endTx = findViewById(R.id.edit_party_end_time);
        subButton = findViewById(R.id.edit_party_submit);

        startTx.setOnClickListener(this);
        endTx.setOnClickListener(this);
        subButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_party_submit:
                handlerSubmitParty();
                break;
            case R.id.edit_party_start_time:
                handlerStartTimeSelect();
                break;
            case R.id.edit_party_end_time:
                handlerStartTimeSelect();
                break;
        }
    }

    private void handlerSubmitParty() {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        String peopleS = peopleEt.getText().toString();
        int people = 0;
        if (!peopleS.isEmpty()) {
            people = Integer.parseInt(peopleS);
        }

        String address = addressEt.getText().toString();

        if (title.isEmpty()) {
            ToastUtil.show("主题不能为空");
            return;
        }

        if (content.isEmpty()) {
            ToastUtil.show("内容不能为空");
            return;
        }

        if (people <= 0) {
            ToastUtil.show("人数不能为0");
            return;
        }

        if (address.isEmpty()) {
            ToastUtil.show("地址不能为空");
            return;
        }

        showLoading();
        AVGlobal.getInstance().getAVImpl().addPrat(title, content, startTime, endTime, address, people, new SaveCallback() {
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

            }
        });
        datePickerDialog.show();
    }

    private void handlerEndTimeSelect() {

    }
}
