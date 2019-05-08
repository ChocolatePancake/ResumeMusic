package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.resume.music.cn.R;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.RegUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;

@Route(path = ModelPathManager.main_editResume)
public class EditResumeActivity extends BaseTitleActivity {
    private ImageView hardImg;

    private EditText nameEt, ageEt, numberEt, jobStatusEt, jobAgeEt, homeAddressEt,
            emilyEt, cardNumberEt, nationalityEt, marriageEt, intentionEt, jobAddressEt,
            salaryEt, jobFlagEt, schoolEt, educationEt, disciplineEt, companyEt, positionEt,
            projectNameEt, companyEameEt, projectDescriptionEt;

    private RadioGroup sexRadioGroup;

    private String resumeId;

    private String head = "";
    private String name = "";
    private String sex = "男";
    private int age = 0;
    private String homeAddress = "";
    private String number = "";
    private String email = "";
    private int jobAge = 0;
    private String jobStatus = "";
    private String cardNumber = "";
    private String nationality = "";
    private String marriage = "";

    private String intention = "";
    private String jobAddress = "";
    private String salary = "";
    private String jobFlag = "";

    private String school = "";
    private String discipline = "";
    private String education = "";
    private String schoolStartTime = "";
    private String schoolEndTime = "";

    private String company = "";
    private String position = "";
    private String jobStartTime = "";
    private String jobEndTime = "";

    private String projectName = "";
    private String companyName = "";
    private String projectDescription = "";
    private String projectStartTime = "";
    private String projectEndTime = "";

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("简历编辑")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_edit_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        hardImg = findViewById(R.id.resume_edit_user_head);
        nameEt = findViewById(R.id.resume_edit_user_name);
        sexRadioGroup = findViewById(R.id.resume_edit_user_sax);
        ageEt = findViewById(R.id.resume_edit_user_age);
        numberEt = findViewById(R.id.resume_edit_user_number);
        jobStatusEt = findViewById(R.id.resume_edit_user_job_status);
        jobAgeEt = findViewById(R.id.resume_edit_user_job_age);
        homeAddressEt = findViewById(R.id.resume_edit_user_home_address);
        emilyEt = findViewById(R.id.resume_edit_user_emily);
        cardNumberEt = findViewById(R.id.resume_edit_user_card_number);
        nationalityEt = findViewById(R.id.resume_edit_user_nationality);
        marriageEt = findViewById(R.id.resume_edit_user_marriage);
        intentionEt = findViewById(R.id.resume_edit_user_intention);
        jobAddressEt = findViewById(R.id.resume_edit_user_job_address);
        salaryEt = findViewById(R.id.resume_edit_user_salary);
        jobFlagEt = findViewById(R.id.resume_edit_user_job_flag);
        schoolEt = findViewById(R.id.resume_edit_user_school);
        educationEt = findViewById(R.id.resume_edit_user_education);
        disciplineEt = findViewById(R.id.resume_edit_user_discipline);
        companyEt = findViewById(R.id.resume_edit_user_company);
        positionEt = findViewById(R.id.resume_edit_user_position);
        projectNameEt = findViewById(R.id.resume_edit_user_project_name);
        companyEameEt = findViewById(R.id.resume_edit_user_company_name);
        projectDescriptionEt = findViewById(R.id.resume_edit_user_project_description);

        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_male:
                        sex = "男";
                        break;
                    case R.id.radioButton_female:
                        sex = "女";
                        break;
                }
            }
        });

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerSubmit();
            }
        });

        resumeId = getIntent().getStringExtra("myResumeId");
        requestDataAndSetDataToView(resumeId);
    }

    private void requestDataAndSetDataToView(String resumeId) {
        if (resumeId == null || resumeId.isEmpty()) {
            return;
        }
        AVObject todo = AVObject.createWithoutData(TABLE_RESUME, resumeId);
        todo.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
                    assignmentToAttributes(object);
                }
            }
        });
    }

    private void assignmentToAttributes(AVObject object) {
        if (object != null) {
            head = object.get(RESUME_HEAD).toString();
            name = object.get(RESUME_NAME).toString();
            sex = object.get(RESUME_SEX).toString();
            age = (int) object.get(RESUME_AGE);
            homeAddress = object.get(RESUME_HOME_ADDRESS).toString();
            number = object.get(RESUME_NUMBER).toString();
            email = object.get(RESUME_E_MAIL).toString();
            jobAge = (int) object.get(RESUME_JOB_AGE);
            jobStatus = object.get(RESUME_JOB_STATUS).toString();
            cardNumber = object.get(RESUME_CARD_NUMBER).toString();
            nationality = object.get(RESUME_NATIONALITY).toString();
            marriage = object.get(RESUME_MARRIAGE).toString();

            intention = object.get(RESUME_INTENTION).toString();
            jobAddress = object.get(RESUME_JOB_ADDRESS).toString();
            salary = object.get(RESUME_SALARY).toString();
            jobFlag = object.get(RESUME_JOB_FLAG).toString();

            school = object.get(RESUME_SCHOOL).toString();
            discipline = object.get(RESUME_DISCIPLINE).toString();
            education = object.get(RESUME_EDUCATION).toString();
            schoolStartTime = object.get(RESUME_SCHOOL_START_TIME).toString();
            schoolEndTime = object.get(RESUME_SCHOOL_END_TIME).toString();

            company = object.get(RESUME_COMPANY).toString();
            position = object.get(RESUME_POSITION).toString();
            jobStartTime = object.get(RESUME_JOB_START_TIME).toString();
            jobEndTime = object.get(RESUME_JOB_END_TIME).toString();

            projectName = object.get(RESUME_PROJECT_NAME).toString();
            companyName = object.get(RESUME_COMPANY_NAME).toString();
            projectDescription = object.get(RESUME_PROJECT_DESCRIPTION).toString();
            projectStartTime = object.get(RESUME_PROJECT_START_TIME).toString();
            projectEndTime = object.get(RESUME_PROJECT_END_TIME).toString();

            setDataToView();
        }
    }

    private void setDataToView() {
        nameEt.setText(name);
        if (sex.equals("男")) {
            sexRadioGroup.check(R.id.radioButton_male);
        } else {
            sexRadioGroup.check(R.id.radioButton_female);
        }

        ageEt.setText(age + "");
        numberEt.setText(number);
        jobStatusEt.setText(jobStatus);
        jobAgeEt.setText(jobAge + "");
        homeAddressEt.setText(homeAddress);
        emilyEt.setText(email);
        cardNumberEt.setText(cardNumber);
        nationalityEt.setText(nationality);
        marriageEt.setText(marriage);

        intentionEt.setText(intention);
        jobAddressEt.setText(jobAddress);
        salaryEt.setText(salary);
        jobFlagEt.setText(jobFlag);

        schoolEt.setText(school);
        educationEt.setText(education);
        disciplineEt.setText(discipline);

        companyEt.setText(company);
        positionEt.setText(position);
        projectNameEt.setText(projectName);
        companyEameEt.setText(companyName);
        projectDescriptionEt.setText(projectDescription);

    }

    private void handlerSubmit() {
        getViewData();
        if (AVUser.getCurrentUser() == null) {
            ToastUtil.show("您还没有登录");
            return;
        }
        if (name == null || name.isEmpty()) {
            ToastUtil.show("请填写姓名");
            return;
        }
        if (sex == null || sex.isEmpty()) {
            ToastUtil.show("请选择性别");
            return;
        }
        if (age < 0) {
            ToastUtil.show("请填写年龄");
            return;
        }
        if (!RegUtils.isMobile(number)) {
            ToastUtil.show("请输入正确的号码");
            return;
        }
        if (jobStatus == null || jobStatus.isEmpty()) {
            ToastUtil.show("请填写工作状态");
            return;
        }
        if (jobAge < 0) {
            ToastUtil.show("请填写工作年龄");
            return;
        }
        if (homeAddress == null || homeAddress.isEmpty()) {
            ToastUtil.show("请输入现居地址");
            return;
        }
        if (resumeId == null || resumeId.isEmpty()) {
            showLoading("正在保存");
            AVGlobal.getInstance().getAVImpl().addResume(head, name, sex, age, homeAddress, number, email,
                    jobAge, jobStatus, cardNumber, nationality, marriage, intention, jobAddress, salary, jobFlag,
                    school, discipline, education, schoolStartTime, schoolEndTime, company, position, jobStartTime,
                    jobEndTime, projectName, companyName, projectDescription, projectStartTime, projectEndTime, new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            hideLoading();
                            if (e == null) {
                                ToastUtil.show("添加成功");
                            } else {
                                ToastUtil.show("失败");
                            }
                        }
                    });
        } else {
            AVGlobal.getInstance().getAVImpl().upDateResume(resumeId, head, name, sex, age, homeAddress, number, email,
                    jobAge, jobStatus, cardNumber, nationality, marriage, intention, jobAddress, salary, jobFlag,
                    school, discipline, education, schoolStartTime, schoolEndTime, company, position, jobStartTime,
                    jobEndTime, projectName, companyName, projectDescription, projectStartTime, projectEndTime, new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            hideLoading();
                            if (e == null) {
                                ToastUtil.show("添加成功");
                            } else {
                                ToastUtil.show("失败");
                            }
                        }
                    });
        }
    }

    private void getViewData() {
        name = nameEt.getText().toString();
        age = ageEt.getText().toString().isEmpty() ? -1 : Integer.valueOf(ageEt.getText().toString());
        homeAddress = homeAddressEt.getText().toString();
        number = numberEt.getText().toString();
        email = emilyEt.getText().toString();
        jobAge = jobAgeEt.getText().toString().isEmpty() ? -1 : Integer.valueOf(jobAgeEt.getText().toString());
        jobStatus = jobStatusEt.getText().toString();
        cardNumber = cardNumberEt.getText().toString();
        nationality = nationalityEt.getText().toString();
        marriage = marriageEt.getText().toString();

        intention = intentionEt.getText().toString();
        jobAddress = jobAddressEt.getText().toString();
        salary = salaryEt.getText().toString();
        jobFlag = jobFlagEt.getText().toString();

        school = schoolEt.getText().toString();
        discipline = disciplineEt.getText().toString();
        education = educationEt.getText().toString();

        company = companyEt.getText().toString();
        education = positionEt.getText().toString();
        projectName = projectNameEt.getText().toString();
        companyName = companyEameEt.getText().toString();
        projectDescription = projectDescriptionEt.getText().toString();
    }
}
