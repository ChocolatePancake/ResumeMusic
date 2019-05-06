package tech.com.commoncore.avdb;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

public interface AVDb {

    void addResume(String name, String sex, int age, String address, String number, String email, String jobStart,
                   String intention, String jobAddress, String salary, String jobFlag, SaveCallback callback);

    void upDateResume(String resumeId, String name, String sex, int age, String address, String number, String email, String jobStart,
                      String intention, String jobAddress, String salary, String jobFlag, SaveCallback callback);

    void requestResume(FindCallback<AVObject> findCallback);

    void requestResume(String userId, FindCallback<AVObject> findCallback);

    void addPrat(String title, String content, String startTime, String endTime, String address, int people, SaveCallback callback);

    void updatePrat(String pratId, String title, String content, String startTime, String endTime, String address, int people, SaveCallback callback);

    void requestPrat(FindCallback<AVObject> findCallback);

    void requestPrat(String userId, FindCallback<AVObject> findCallback);

    void addPlan(String title, String content, String sTime, String eTime, String status, SaveCallback callback);

    void updatePlan(String planId, String type, String title, String content, String sTime, String eTime, String status, SaveCallback callback);

    void requestPlan(FindCallback<AVObject> findCallback);

    void requestPlan(String userId, FindCallback<AVObject> findCallback);

    AVFile getAVFileByPath(String path, String fileName);

    void addFile(String file, int grade, String fileName, String fileType, String content, SaveCallback callback);

    void requestFile(String fileType, int grade, FindCallback<AVObject> findCallback);

    void requestFile(String userId, String fileType,FindCallback<AVObject> findCallback);
}
