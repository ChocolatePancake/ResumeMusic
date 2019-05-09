package com.resume.music.cn.adapter;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.resume.music.cn.R;
import com.resume.music.cn.bus.ResumeRefreshEvent;
import com.vise.xsnow.event.BusManager;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.plog;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.manager.ModelPathManager.main_editResume;

public class ResumeAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {


    public ResumeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AVObject item) {
        String resumeId = item.getObjectId();
        String head = item.get(RESUME_HEAD).toString();
        String name = item.get(RESUME_NAME).toString();
        String sex = item.get(RESUME_SEX).toString();
        int age = (int) item.get(RESUME_AGE);
        String homeAddress = item.get(RESUME_HOME_ADDRESS).toString();
        String number = item.get(RESUME_NUMBER).toString();
        String email = item.get(RESUME_E_MAIL).toString();
        int jobAge = (int) item.get(RESUME_JOB_AGE);
        String jobStatus = item.get(RESUME_JOB_STATUS).toString();
        String cardNumber = item.get(RESUME_CARD_NUMBER).toString();
        String nationality = item.get(RESUME_NATIONALITY).toString();
        String marriage = item.get(RESUME_MARRIAGE).toString();
        String intention = item.get(RESUME_INTENTION).toString();
        String jobAddress = item.get(RESUME_JOB_ADDRESS).toString();
        String salary = item.get(RESUME_SALARY).toString();
        String jobFlag = item.get(RESUME_JOB_FLAG).toString();
        String school = item.get(RESUME_SCHOOL).toString();
        String discipline = item.get(RESUME_DISCIPLINE).toString();
        String education = item.get(RESUME_EDUCATION).toString();
        String schoolStartTime = item.get(RESUME_SCHOOL_START_TIME).toString();
        String schoolEndTime = item.get(RESUME_SCHOOL_END_TIME).toString();
        String company = item.get(RESUME_COMPANY).toString();
        String position = item.get(RESUME_POSITION).toString();
        String jobStartTime = item.get(RESUME_JOB_START_TIME).toString();
        String jobEndTime = item.get(RESUME_JOB_END_TIME).toString();
        String projectName = item.get(RESUME_PROJECT_NAME).toString();
        String companyName = item.get(RESUME_COMPANY_NAME).toString();
        String projectDescription = item.get(RESUME_PROJECT_DESCRIPTION).toString();
        String projectStartTime = item.get(RESUME_PROJECT_START_TIME).toString();
        String projectEndTime = item.get(RESUME_PROJECT_END_TIME).toString();

        ImageView headImage = helper.itemView.findViewById(R.id.edit_resume_head);
        GlideManager.loadImg(head, headImage, R.drawable.icon_resume_default_head);

        helper.setText(R.id.edit_resume_title, (intention == null || intention.isEmpty() ? "未确认职位" : intention));
        helper.setText(R.id.edit_resume_name, name);
        helper.setText(R.id.edit_resume_sex, sex);
        helper.setText(R.id.edit_resume_age, age + "岁");
        helper.setText(R.id.edit_resume_live_address, homeAddress);
        helper.setText(R.id.edit_resume_job_age, jobAge + "工作经验");
        helper.setText(R.id.edit_resume_number, number);
        helper.setText(R.id.edit_resume_email, email);
        helper.setText(R.id.edit_resume_job_stuart, jobStatus);
        helper.setText(R.id.edit_resume_position, intention);
        helper.setText(R.id.edit_resume_city, jobAddress);
        helper.setText(R.id.edit_resume_salary, salary);
        helper.setText(R.id.edit_resume_job_flag, jobFlag);
        helper.setText(R.id.edit_resume_education_date, schoolStartTime + "-" + schoolEndTime);
        helper.setText(R.id.edit_resume_school, school);
        helper.setText(R.id.edit_resume_education, education);
        helper.setText(R.id.edit_resume_profession, discipline);
        helper.setText(R.id.edit_resume_job_date, jobStartTime + "-" + jobEndTime);
        helper.setText(R.id.edit_resume_job_company, company);
        helper.setText(R.id.edit_resume_job_position, position);
        helper.setText(R.id.edit_resume_project_date, projectStartTime + "-" + projectEndTime);
        helper.setText(R.id.edit_resume_project, projectName);
        helper.setText(R.id.edit_resume_project_company, companyName);

        helper.itemView.findViewById(R.id.edit_resume_item_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(main_editResume)
                        .withString("myResumeId", item.getObjectId())
                        .navigation();
            }
        });

        helper.itemView.findViewById(R.id.edit_resume_item_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusManager.getBus().post(new ResumeRefreshEvent());
            }
        });

        helper.itemView.findViewById(R.id.edit_resume_item_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
