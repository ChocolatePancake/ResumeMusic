package com.yyjj.my.db;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVFriendshipQuery;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.avos.avoscloud.callback.AVFriendshipCallback;
import com.vise.utils.assist.DateUtil;
import com.yyjj.my.cache.CacheData;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import tech.com.commoncore.utils.ToastUtil;

/**
 * Time:2018/11/21
 * Desc:
 */
public class AVDbManager {

//    public  static final String TABLE_SWITCH ="Switch";
//    public  static final String SWITCH_NEED_SWITCH ="switch";
//    public  static final String SWITCH_URL ="url";
//    public  static final String SWITCH_TOOLBAR_COLOR ="titleColor";
//    public  static final String SWITCH_CHANNEL ="channel";


    public  static final String TABLE_SWITCH ="CheckVersion";
    public  static final String SWITCH_NEED_SWITCH ="versionCode";   // 0/空：正常界⾯面，1： 跳转到H5的界⾯面；
    public  static final String SWITCH_URL ="versionUrl";
    public  static final String SWITCH_TOOLBAR_COLOR ="versionBg";   //为H5界⾯面状态栏⽂文字颜⾊色，输⼊入以 0x 开头的颜⾊色 如0x000000代表⿊黑⾊色
    public  static final String SWITCH_NAVIGATION_COLOR ="versionCol"; //(无用字段) 字段为H5界⾯面导航栏背景⾊色，输⼊入以 0x 开头的颜⾊色 如0x000000代表⿊黑⾊色；
    public  static final String SWITCH_CHANNEL ="channel";

    public static final String SIGN_RECORD = "SignRecord"; //签到表

    public  static final String TABLE_USER ="_User";
    public  static final String USER_NICKE_NAME ="nickName";  //昵称
    public  static final String USER_SIGN="sign";  //个性签名
    public  static final String USER_USERNAME="username";  //个性签名
    public  static final String USER_HEAD_ICON="icon";  //头像
    public static final String USER_INTEGRAL = "integral";//积分
    public  static final String USER_PHONE="mobilePhoneNumber";  //电话
    public  static final String USER_MALE="male";  //电话
    public  static final String USER_FOCUS="focus";  //关注

    public  static final String TABLE_COLELCTION="Collection";  //文章收藏
    public  static final String COLELCTION_USER="user";  //对应的用户
    public  static final String COLELCTION_CONTENT="content";  //内容
    public  static final String COLELCTION_CATEGORY_ID="category_id";  //类别id
    public  static final String COLELCTION_ID="id";  //id
    public  static final String COLELCTION_POST_TITLE="post_title";  //标题
    public  static final String COLELCTION_PUBLISH_TIME="published_time";  //发布时间
    public  static final String COLELCTION_MORE="more";  //图片
    public  static final String COLELCTION_JSON="json";  //整个实体数据.


    public  static final String TABLE_OPTINAL="Optional";  //自选
    public  static final String OPTINAL_CODES="codes";  //自选--code
    public  static final String OPTINAL_NAME="name";  //自选--name
    public  static final String OPTINAL_USER="user";  //自选 -- 用户


    public  static final String TABLE_FEEDBACK="Feedback";  //反馈
    public  static final String FEEDBACK_USER="user";  //反馈
    public  static final String FEEDBACK_CONTENT="content";  //反馈

    public static final String TABLE_COMMUNITY = "Community"; //鼎圈交流表
    public static final String COMMUNITY_IMG_LIST = "imgList";  //图片
    public static final String COMMUNITY_USER = "user";        //发表者
    public static final String COMMUNITY_CONTENT = "content";  //内容
    public static final String COMMUNITY_LIKENUM = "likeCount";  //点赞数
    public static final String COMMUNITY_COMMENT_COUNT = "commentCount";  //评论
    public static final String COMMUNITY_HASDELETED = "isDelete";  //话题删除
    public static final String COMMUNITY_VIDEO_NAME = "videoName";  //视频的名称

    public static final String TABLE_COMMENT = "Comment"; //评论表
    public static final String COMMENT_COMMUNITY = "community"; //评论表--关联的圈子话题
    public static final String COMMENT_CONTENT = "content"; //评论表-评论内容
    public static final String COMMENT_USER = "user"; //评论表-关联的发布人
    public static final String COMMENT_HAS_DELETED = "isDelete"; //评论表-关联的发布人

    public static final String TABLE_MESSAGE = "Message";//消息表
    public static final String MESSAGE_USER = "user";//消息表--用户
    public static final String MESSAGE_CREAT_USER = "creatUser";//消息表--创建用户
    public static final String MESSAGE_COMMUNITY = "community";//消息表--圈子话题
    public static final String MESSAGE_COMMENT = "comment";//消息表--评论
    public static final String MESSAGE_CONTENT = "content";//消息表--评论内容
    public static final String MESSAGE_TYPE = "type";//消息表--类型 1--评论

    public static final String TABLE_COLLECT = "Collect"; //个人数据收藏
    public static final String COLLECT_USER = "user"; //个人数据收藏 --收藏者
    public static final String COLLECT_TYPE = "type"; //收藏 -- 类型
    public static final String COLLECT_COMMUNITY = "community"; //收藏 -- 讨论
    public static final String COLLECT_GOODS = "goods"; //收藏 -- 商品


    public static final String TABLE_BOOKS = "Book";  //书籍库
    public static final String BOOKS_NAME = "name";  //书籍库
    public static final String BOOKS_PRICE = "price";  //书籍库
    public static final String BOOKS_PIC = "pic";  //书籍库
    public static final String BOOKS_HOT = "hot";  //书籍库
    public static final String BOOKS_COUNT_COLLECT = "collectCount";  //书籍库
    public static final String BOOKS_AUTHOR = "author";  //书籍库
    public static final String BOOKS_PUBLISHING = "publishing";  //书籍库
    public static final String BOOKS_DEC = "description";  //书籍库
    public static final String BOOKS_RELEASE_TIME = "releaseTime";  //书籍库


    public static final String TABLE_AUTHOR = "Author"; //书籍作者
    public static final String AUTHOR_NAME = "name"; //书籍作者
    public static final String AUTHOR_ICON = "icon"; //书籍作者
    public static final String AUTHOR_FANS_COUNT = "fansCount"; //书籍作者
    public static final String AUTHOR_ARTICLE_COUNT = "articleCount"; //书籍作者

    /**
     * 开关接口
     * @param callback
     */
    public static void requestAuthors(FindCallback<AVObject> callback){
        AVQuery avQuery = new AVQuery(TABLE_AUTHOR);
        avQuery.findInBackground(callback);
    }

    /**
     * 添加作者关注列表
     */
    public static Boolean changeAuthorFocus(List focusList){
        AVUser currentUser = AVUser.getCurrentUser();
        if(currentUser!=null){
            currentUser.put(USER_FOCUS,focusList);
            currentUser.saveInBackground();
            return true;
        }else{
            //            当前用户未登录;
            return false;
        }
    }

    /**
     * 获取签到
     */
    public static void requestSigns(String month,FindCallback<AVObject> callback){
        AVQuery<AVObject> query = new AVQuery<>(SIGN_RECORD);
        if(month!=null){
            query.whereEqualTo("signMonth", month);
        }
        query.whereEqualTo("userId", AVUser.getCurrentUser().getObjectId());
        query.findInBackground(callback);
    }
    /**
     *  保存签到
     */
    public static void signSave(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = (Calendar.getInstance().get(Calendar.MONTH) +1);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String date = DateUtil.getYmd(System.currentTimeMillis());

        AVObject todoFolder = new AVObject(SIGN_RECORD);// 构建签到对象
        todoFolder.put("userId", AVUser.getCurrentUser().getObjectId());// 设置签到者
        todoFolder.put("signYear", year);//  年
        todoFolder.put("signMonth", month);//  月
        todoFolder.put("signDay", day);//  日
        todoFolder.put("signDate", date);//   完整日期
        todoFolder.saveInBackground();// 保存到服务端

        int currentIntegral = AVUser.getCurrentUser().getInt(USER_INTEGRAL)  + 5;
        AVUser user =AVUser.getCurrentUser();
        user.put(USER_INTEGRAL,currentIntegral);
        user.saveInBackground();
    }


    /**
     * 开关接口
     * @param callback
     */
    public static void requestTableSwitch(FindCallback<AVObject> callback){
        AVQuery avQuery = new AVQuery(TABLE_SWITCH);
        avQuery.findInBackground(callback);
    }

    /**
     * 请求文章收藏
     * @param callback
     */
    public static void requestCollection(FindCallback<AVObject> callback){
        AVQuery query = new AVQuery(TABLE_COLELCTION);
        query.whereEqualTo(COLELCTION_USER,AVUser.getCurrentUser());
        query.findInBackground(callback);
    }

   /**
     * 请求收藏
     * @param callback
     */
    public static void requestCollectionCount(CountCallback callback){
        AVQuery query = new AVQuery(TABLE_COLLECT);
        query.whereEqualTo(COLLECT_USER,AVUser.getCurrentUser());
        query.countInBackground(callback);
    }


    /**
     * 新旧密码修改
     * @param oldPassword
     * @param newPassword
     * @param callBack
     */
    public  static void changePassword(String oldPassword, final String newPassword, UpdatePasswordCallback callBack){
        if(AVUser.getCurrentUser() ==null){
            ToastUtil.show("请先登录");
            return;
        }
        AVUser.getCurrentUser().updatePasswordInBackground(oldPassword, newPassword, callBack);
    }


    public static void requestCommunity(int count,FindCallback<AVObject> callback){
        AVQuery query = new AVQuery(TABLE_COMMUNITY);
        query.include(COMMUNITY_USER);
        if(count != 0){
            query.limit(count);
        }
        query.findInBackground(callback);
    }

    public static void requestCommunity(int pageSize,int page,List<AVUser> focusUser ,FindCallback<AVObject> callback){

        AVQuery<AVObject> query = new AVQuery<>(AVDbManager.TABLE_COMMUNITY);
        if(focusUser != null){
        query.whereContainedIn(AVDbManager.COMMUNITY_USER,focusUser);

        }
        query.include(AVDbManager.COMMUNITY_USER);
        query.setLimit(pageSize);
        query.setSkip(page * pageSize);
        query.orderByDescending("createdAt");
        query.findInBackground(callback);
    }

    /**
     * 请求圈子对应的评论列表
     * @param community
     * @param callback
     */
    public static void requestComment(AVObject community,FindCallback<AVObject> callback){
        AVQuery<AVObject> query = new AVQuery<>(TABLE_COMMENT);
        query.whereEqualTo(COMMENT_COMMUNITY, community);
        query.include(COMMENT_USER);
        query.include(COMMENT_COMMUNITY);
        query.orderByDescending("createdAt");
        query.findInBackground(callback);

    }

    /**
     * 删除当前用户圈子
     * @param callback
     */
    public static void deleteCommunity(AVObject avObject, @NonNull DeleteCallback callback){
//        avObject.put(COMMUNITY_IS_DELETED,true);
//        avObject.saveInBackground(callback);
        avObject.deleteInBackground(callback);
    }

    public static void saveComment(AVObject community,String content,SaveCallback callback){

        AVObject comment = new AVObject(TABLE_COMMENT);
        comment.put(COMMENT_CONTENT,content);
        comment.put(COMMENT_USER,AVUser.getCurrentUser());
        comment.put(COMMENT_COMMUNITY,community);

        comment.saveInBackground(callback);

        //1.创建消息
        try {
            createMessage(content,community,community.getAVUser(COMMENT_USER));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.话题的 数量自增
        try {
            community.increment(AVDbManager.COMMUNITY_COMMENT_COUNT, 1);
            community.saveInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void createMessage(String content,AVObject community,AVUser communityUser){
        AVObject object = AVObject.create(TABLE_MESSAGE);
        object.put(MESSAGE_USER,communityUser);  //消息接收者
        object.put(MESSAGE_CREAT_USER,AVUser.getCurrentUser());   //消息创建者
        object.put(MESSAGE_CONTENT,content);            //消息内容
        object.put(MESSAGE_COMMUNITY,community);        //消息的追溯 话题
        object.saveInBackground();


    }
    /**
     * 添加关注
     * @param userObjectId
     * @param callback
     */
    public static void AddFocus(String userObjectId,FollowCallback<AVObject> callback){
        //关注
        AVUser.getCurrentUser().followInBackground(userObjectId, callback);
    }

    /**
     * 取消关注
     * @param userObjectId
     * @param callback
     */
    public static void cancelFocus(String userObjectId,FollowCallback<AVObject> callback){
        //关注
        //取消关注
        AVUser.getCurrentUser().unfollowInBackground(userObjectId,callback);
    }

    public static void likeChange(AVObject communitObj ,int count){
        communitObj.increment(AVDbManager.COMMUNITY_LIKENUM, count);
        communitObj.saveInBackground();
    }
    /**
     * 获取并保存 关注者和被关注者
     * @param callBack
     */
    public static void  refreshFloower(final IcallBack callBack){
        if(AVUser.getCurrentUser() == null){
            return;
        }
        AVFriendshipQuery query = AVUser.friendshipQuery(AVUser.getCurrentUser().getObjectId(), AVUser.class);
        query.include("followee");
        query.include("follower");
        query.getInBackground(new AVFriendshipCallback() {
            @Override
            public void done(AVFriendship friendship, AVException e) {
                if (e == null) {
                    List<AVUser> fansList = friendship.getFollowers();//获取粉丝
                    List<AVUser> followList = friendship.getFollowees();//关注列表
                    CacheData.setFollowList(followList);
                    CacheData.setFansList(fansList);
                    if(callBack!=null){
                        callBack.callback(fansList,followList);
                    }
                } else {

                }
            }
        });

    }
    public interface IcallBack{
        void callback(List<AVUser> fansList, List<AVUser> followList);
    }

    /**
     * 修改昵称
     * @param nick
     */
    public static void updateNickAndMale(String nick,String male) {
        AVUser user=  AVUser.getCurrentUser();
        user.put(USER_NICKE_NAME,nick);
        user.put(USER_MALE,male);
        user.saveInBackground();
    }

    /**
     * 删除当前用户评论
     * @param callback
     */
    public static void deleteComment(AVObject avObject, @NonNull DeleteCallback callback){
        avObject.deleteInBackground(callback);
    }
    /**
     * 删除当前用户评论
     * @param callback
     */
    public static void deleteMessage(AVObject avObject, @NonNull DeleteCallback callback){
        avObject.deleteInBackground(callback);
    }

    /**
     * 消息列表
     * @param callback
     */
    public static void requestMessageList(int page,int pagesize, @NonNull  FindCallback<AVObject> callback){

        AVQuery<AVObject> query = new AVQuery<>(TABLE_MESSAGE);
        query.whereEqualTo(MESSAGE_USER,AVUser.getCurrentUser());
        query.include(MESSAGE_CREAT_USER);
        query.include(MESSAGE_USER);
        query.include(MESSAGE_COMMUNITY);
        query.include(MESSAGE_COMMUNITY+"."+COMMENT_USER);

        query.setLimit(pagesize);
        query.setSkip(page * pagesize);
        query.findInBackground(callback);
    }

    /**
     * 消息的数量
     * @param countCallback
     */
    public static void requestMessageSize(CountCallback countCallback){
        AVQuery<AVObject> query = new AVQuery<>(TABLE_MESSAGE);
        query.whereEqualTo(AVDbManager.MESSAGE_USER,AVUser.getCurrentUser());
        query.countInBackground(countCallback);
    }

    public  static void requestUsers(int page,int pageSize, @NonNull  FindCallback<AVObject> callback){
        AVQuery<AVObject> query = new AVQuery<>(TABLE_USER);
//        query.setLimit(pageSize);
//        query.setSkip(page * pageSize);
        query.findInBackground(callback);
    }

    public static void addCollect(AVObject contentObj,String type,SaveCallback callback){
        AVObject collect =  AVObject.create(TABLE_COLLECT);
        if(type.equals(COLLECT_COMMUNITY)){
            collect.put(COLLECT_COMMUNITY,contentObj);
        }else{
            collect.put(COLLECT_GOODS,contentObj);
        }

        collect.put(COLLECT_TYPE,type);
        collect.put(COLLECT_USER,AVUser.getCurrentUser());
        collect.saveInBackground(callback);
    }

    public static void cancelCollect(AVObject contentObj, String type, final IColleactCallBack callBack){
        AVQuery<AVObject>  query = new AVQuery<>(TABLE_COLLECT);
        if(type.equals(COLLECT_COMMUNITY)){
            query.whereEqualTo(COLLECT_COMMUNITY,contentObj);
        }else if(type.equals(COLLECT_GOODS)){
            query.whereEqualTo(COLLECT_GOODS,contentObj);
        }

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e ==null && list!=null && list.size()>0){
                    list.get(0).deleteInBackground();
                    if(callBack!=null){
                        callBack.callback(true);
                    }
                }else{
                    if(callBack!=null){
                        callBack.callback(false);
                    }
                }
            }
        });
    }

    public interface IColleactCallBack{
        void callback(boolean isSuccess);
    }

    /**
     * 收藏的的 书
     * @param callback
     */
    public static void requestCollectBook(FindCallback<AVObject> callback){
        AVQuery<AVObject>  query1 = new AVQuery<>(TABLE_COLLECT);
        query1.whereEqualTo(COLLECT_TYPE,COLLECT_GOODS);

        AVQuery<AVObject>  query2 = new AVQuery<>(TABLE_COLLECT);
        query2.whereEqualTo(COLLECT_USER,AVUser.getCurrentUser());

        AVQuery query = AVQuery.and(Arrays.asList(query1, query2));
        query.include(COLLECT_USER);
        query.include(COLLECT_GOODS);
        query.include(COLLECT_COMMUNITY);
        query.findInBackground(callback);
        query.findInBackground(callback);
    }

    /**
     * 收藏的话题
     * @param callback
     */
    public static void requestCollectCommunity(FindCallback<AVObject> callback){
        AVQuery<AVObject>  query1 = new AVQuery<>(TABLE_COLLECT);
        query1.whereEqualTo(COLLECT_TYPE,COLLECT_COMMUNITY);
        AVQuery<AVObject>  query2 = new AVQuery<>(TABLE_COLLECT);
        query2.whereEqualTo(COLLECT_USER,AVUser.getCurrentUser());

        AVQuery query = AVQuery.and(Arrays.asList(query1, query2));
        query.include(COLLECT_USER);
        query.include(COLLECT_GOODS);
        query.include(COLLECT_COMMUNITY);
        query.include(COLLECT_COMMUNITY+"."+COMMENT_USER);
        query.findInBackground(callback);
    }
    /**
     * 图书
     */
    public static void requestBooks(FindCallback callback){
        AVQuery<AVObject> query = new AVQuery<>(TABLE_BOOKS);
        query.findInBackground(callback);
    }

    public static void bookLikeChange(AVObject avObject ,int count){
        avObject.increment(AVDbManager.BOOKS_COUNT_COLLECT, count);
        avObject.saveInBackground();
    }
}
