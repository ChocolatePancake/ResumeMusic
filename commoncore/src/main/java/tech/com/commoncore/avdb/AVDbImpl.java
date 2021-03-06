package tech.com.commoncore.avdb;


import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.io.FileNotFoundException;
import java.util.Arrays;

import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;

public class AVDbImpl implements AVDb {


    @Override
    public void addResume(String head, String name, String sex, int age, String homeAddress, String number,
                          String email, int jobAge, String jobStatus, String cardNumber, String nationality,
                          String marriage, String intention, String jobAddress, String salary, String jobFlag,
                          String school, String discipline, String education, String schoolStartTime,
                          String schoolEndTime, String company, String position, String jobStartTime,
                          String jobEndTime, String projectName, String companyName, String projectDescription,
                          String projectStartTime, String projectEndTime, SaveCallback callback) {
        AVObject avObject = new AVObject(TABLE_RESUME);
        avObject.put(RESUME_USER, AVUser.getCurrentUser().getObjectId());
        avObject.put(RESUME_HEAD, head);
        avObject.put(RESUME_NAME, name);
        avObject.put(RESUME_SEX, sex);
        avObject.put(RESUME_AGE, age);
        avObject.put(RESUME_HOME_ADDRESS, homeAddress);
        avObject.put(RESUME_NUMBER, number);
        avObject.put(RESUME_E_MAIL, email);
        avObject.put(RESUME_JOB_AGE, jobAge);
        avObject.put(RESUME_JOB_STATUS, jobStatus);
        avObject.put(RESUME_CARD_NUMBER, cardNumber);
        avObject.put(RESUME_NATIONALITY, nationality);
        avObject.put(RESUME_MARRIAGE, marriage);

        avObject.put(RESUME_INTENTION, intention);
        avObject.put(RESUME_JOB_ADDRESS, jobAddress);
        avObject.put(RESUME_SALARY, salary);
        avObject.put(RESUME_JOB_FLAG, jobFlag);

        avObject.put(RESUME_SCHOOL, school);
        avObject.put(RESUME_DISCIPLINE, discipline);
        avObject.put(RESUME_EDUCATION, education);
        avObject.put(RESUME_SCHOOL_START_TIME, schoolStartTime);
        avObject.put(RESUME_SCHOOL_END_TIME, schoolEndTime);

        avObject.put(RESUME_COMPANY, company);
        avObject.put(RESUME_POSITION, position);
        avObject.put(RESUME_JOB_START_TIME, jobStartTime);
        avObject.put(RESUME_JOB_END_TIME, jobEndTime);

        avObject.put(RESUME_PROJECT_NAME, projectName);
        avObject.put(RESUME_COMPANY_NAME, companyName);
        avObject.put(RESUME_PROJECT_DESCRIPTION, projectDescription);
        avObject.put(RESUME_PROJECT_START_TIME, projectStartTime);
        avObject.put(RESUME_PROJECT_END_TIME, projectEndTime);
        avObject.saveInBackground(callback);
    }

    @Override
    public void upDateResume(String resumeId, String head, String name, String sex, int age, String homeAddress,
                             String number, String email, int jobAge, String jobStatus, String cardNumber,
                             String nationality, String marriage, String intention, String jobAddress,
                             String salary, String jobFlag, String school, String discipline, String education,
                             String schoolStartTime, String schoolEndTime, String company, String position,
                             String jobStartTime, String jobEndTime, String projectName, String companyName,
                             String projectDescription, String projectStartTime, String projectEndTime, SaveCallback callback) {
        AVObject avObject = AVObject.createWithoutData(TABLE_RESUME, resumeId);
        avObject.put(RESUME_HEAD, head);
        avObject.put(RESUME_NAME, name);
        avObject.put(RESUME_SEX, sex);
        avObject.put(RESUME_AGE, age);
        avObject.put(RESUME_HOME_ADDRESS, homeAddress);
        avObject.put(RESUME_NUMBER, number);
        avObject.put(RESUME_E_MAIL, email);
        avObject.put(RESUME_JOB_AGE, jobAge);
        avObject.put(RESUME_JOB_STATUS, jobStatus);
        avObject.put(RESUME_CARD_NUMBER, cardNumber);
        avObject.put(RESUME_NATIONALITY, nationality);
        avObject.put(RESUME_MARRIAGE, marriage);

        avObject.put(RESUME_INTENTION, intention);
        avObject.put(RESUME_JOB_ADDRESS, jobAddress);
        avObject.put(RESUME_SALARY, salary);
        avObject.put(RESUME_JOB_FLAG, jobFlag);

        avObject.put(RESUME_SCHOOL, school);
        avObject.put(RESUME_DISCIPLINE, discipline);
        avObject.put(RESUME_EDUCATION, education);
        avObject.put(RESUME_SCHOOL_START_TIME, schoolStartTime);
        avObject.put(RESUME_SCHOOL_END_TIME, schoolEndTime);

        avObject.put(RESUME_COMPANY, company);
        avObject.put(RESUME_POSITION, position);
        avObject.put(RESUME_JOB_START_TIME, jobStartTime);
        avObject.put(RESUME_JOB_END_TIME, jobEndTime);

        avObject.put(RESUME_PROJECT_NAME, projectName);
        avObject.put(RESUME_COMPANY_NAME, companyName);
        avObject.put(RESUME_PROJECT_DESCRIPTION, projectDescription);
        avObject.put(RESUME_PROJECT_START_TIME, projectStartTime);
        avObject.put(RESUME_PROJECT_END_TIME, projectEndTime);
        avObject.saveInBackground(callback);
    }

    @Override
    public void requestResume(FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_RESUME);
        query.findInBackground(findCallback);
    }

    @Override
    public void requestResume(String userId, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_RESUME);
        query.whereEqualTo(RESUME_USER, AVUser.getCurrentUser().getObjectId());
        query.findInBackground(findCallback);
    }

    @Override
    public void addPrat(String title, String content, String startTime, String endTime, String address, int people, SaveCallback callback) {
        AVObject avObject = new AVObject(TABLE_PARTY);
        avObject.put(PARTY_USER, AVUser.getCurrentUser().getObjectId());
        avObject.put(PARTY_TITLE, title);
        avObject.put(PARTY_CONTENT, content);
        avObject.put(PARTY_START_TIME, startTime);
        avObject.put(PARTY_END_TIME, endTime);
        avObject.put(PARTY_ADDRESS, address);
        avObject.put(PARTY_PEOPLE, people);
        avObject.put(PARTY_STATUS, STATUS_TYPE_UNSTART);
        avObject.put(TARGET_AVUSER, AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId()));
        avObject.saveInBackground(callback);
    }

    @Override
    public void updatePrat(String pratId, String title, String content, String startTime, String endTime, String address, int people, SaveCallback callback) {
        AVObject avObject = AVObject.createWithoutData(TABLE_PARTY, pratId);
        avObject.put(PARTY_TITLE, title);
        avObject.put(PARTY_CONTENT, content);
        avObject.put(PARTY_START_TIME, startTime);
        avObject.put(PARTY_END_TIME, endTime);
        avObject.put(PARTY_ADDRESS, address);
        avObject.put(PARTY_PEOPLE, people);
        avObject.put(PARTY_STATUS, STATUS_TYPE_UNSTART);
        avObject.saveInBackground(callback);
    }

    @Override
    public void requestPrat(FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_PARTY);
        query.include(TARGET_AVUSER);
        query.findInBackground(findCallback);
    }

    @Override
    public void requestPrat(String userId, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_PARTY);
        query.whereEqualTo(PARTY_USER, userId);
        query.findInBackground(findCallback);
    }

    @Override
    public void addPlan(String title, String content, String sTime, String eTime, String status, SaveCallback callback) {
        AVObject avObject = new AVObject(TABLE_PLAN);
        avObject.put(PLAN_USER, AVUser.getCurrentUser().getObjectId());
        avObject.put(PLAN_TITLE, title);
        avObject.put(PLAN_CONTENT, content);
        avObject.put(PLAN_START_TIME, sTime);
        avObject.put(PLAN_END_TIME, eTime);
        avObject.put(PLAN_STATUS, status);
        avObject.put(TARGET_AVUSER, AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId()));
        avObject.saveInBackground(callback);
    }

    @Override
    public void updatePlan(String planId, String type, String title, String content, String sTime, String eTime, String status, SaveCallback callback) {
        AVObject avObject = AVObject.createWithoutData(TABLE_PLAN, planId);
        avObject.put(PLAN_TYPE, type);
        avObject.put(PLAN_TITLE, title);
        avObject.put(PLAN_CONTENT, content);
        avObject.put(PLAN_START_TIME, sTime);
        avObject.put(PLAN_END_TIME, eTime);
        avObject.put(PLAN_STATUS, status);
        avObject.saveInBackground(callback);
    }

    @Override
    public void requestPlan(FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_PLAN);
        query.include(TARGET_AVUSER);
        query.findInBackground(findCallback);
    }

    @Override
    public void requestPlan(String userId, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_PLAN);
        query.whereEqualTo(PLAN_USER, AVUser.getCurrentUser().getObjectId());
        query.include(TARGET_AVUSER);
        query.findInBackground(findCallback);
    }

    @Override
    public AVFile getAVFileByPath(String path, String fileName) {
        try {
            final AVFile avFile = AVFile.withAbsoluteLocalPath(fileName, path + "/" + fileName);
            return avFile;
        } catch (FileNotFoundException e) {
            ToastUtil.show("文件未知");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addFile(String file, int grade, String fileName, String fileType, String content, SaveCallback callback) {
        AVObject object = new AVObject(TABLE_MEDIA);
        object.put(MEDIA_USER, AVUser.getCurrentUser().getObjectId());
        object.put(MEDIA_FILE, file);
        object.put(MEDIA_GRADE, grade);
        object.put(MEDIA_FILE_NAME, fileName);
        object.put(MEDIA_FILE_TYPE, fileType);
        object.put(MEDIA_CONTENT, content);
        object.put(MEDIA_LIKE_COUNT, 0);
        object.put(MEDIA_COMMENT_COUNT, 0);
        object.put(MEDIA_IS_DELETED, 0);
        object.put(TARGET_AVUSER, AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId()));
        object.saveInBackground(callback);
    }

    @Override
    public void requestFile(String fileType, int grade, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> typeQuery = new AVQuery<>(TABLE_MEDIA);
        typeQuery.whereEqualTo(MEDIA_FILE_TYPE, fileType);
        AVQuery<AVObject> gradeQuery = new AVQuery<>(TABLE_MEDIA);
        gradeQuery.whereEqualTo(MEDIA_GRADE, grade);

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(typeQuery, gradeQuery));
        query.include(TARGET_AVUSER);
        query.findInBackground(findCallback);
    }

    @Override
    public void requestFile(String userId, String fileType, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> typeQuery = new AVQuery<>(TABLE_MEDIA);
        typeQuery.whereEqualTo(MEDIA_FILE_TYPE, fileType);
        AVQuery<AVObject> userQuery = new AVQuery<>(TABLE_MEDIA);
        userQuery.whereEqualTo(MEDIA_USER, AVUser.getCurrentUser().getObjectId());

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(typeQuery, userQuery));
        query.include(TARGET_AVUSER);
        query.findInBackground(findCallback);
    }
}
