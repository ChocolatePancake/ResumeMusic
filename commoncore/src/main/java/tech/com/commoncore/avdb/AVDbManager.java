package tech.com.commoncore.avdb;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.UpdatePasswordCallback;

import java.util.List;

import tech.com.commoncore.utils.ToastUtil;


public class AVDbManager {

    /*用户表相关字段*/
    public static final String USER_NICKE_NAME = "nickName";                //昵称
    public static final String USER_SIGN = "sign";                          //个性签名
    public static final String USER_USERNAME = "username";                  //个性签名
    public static final String USER_HEAD_ICON = "icon";                     //头像
    public static final String USER_INTEGRAL = "integral";                  //积分
    public static final String USER_PHONE = "mobilePhoneNumber";            //电话
    public static final String USER_MALE = "male";                          //电话
    public static final String USER_FOCUS = "focus";                        //关注

    /*签到表相关字段*/
    public static final String TABLE_RECORD = "SignRecord";                 //表名
    public static final String RECORD_USER = "recordUser";                  //用户
    public static final String RECORD_ISRECORD = "isRecord";                //是否签到
    public static final String RECORD_DATE = "recordDate";                  //签到时间
    public static final String RECORD_EVENT = "recordEvent";                //签到对应事件

    /*文章表相关字段*/
    public static final String TABLE_COLELCTION = "Collection";             //文章收藏
    public static final String COLELCTION_USER = "user";                    //对应的用户
    public static final String COLELCTION_CONTENT = "content";              //内容
    public static final String COLELCTION_CATEGORY_ID = "category_id";      //类别id
    public static final String COLELCTION_POST_TITLE = "post_title";        //标题
    public static final String COLELCTION_PUBLISH_TIME = "published_time";  //发布时间
    public static final String COLELCTION_MORE = "more";                    //图片
    public static final String COLELCTION_JSON = "json";                    //整个实体数据.

    /*自选表相关字段*/
    public static final String TABLE_OPTINAL = "Optional";                  //自选
    public static final String OPTINAL_ID = "id";                           //id
    public static final String OPTINAL_CODES = "code";                      //自选--code
    public static final String OPTINAL_NAME = "name";                       //自选--name
    public static final String OPTINAL_USER = "user";                       //自选 -- 用户

    /*反馈信息表相关字段*/
    public static final String TABLE_FEEDBACK = "Feedback";                 //表名
    public static final String FEEDBACK_ID = "id";                          //id
    public static final String FEEDBACK_USER = "user";                      //用户
    public static final String FEEDBACK_CONTENT = "content";                //内容
    public static final String FEEDBACK_CONTACT = "contact";                //联系方式

    /*圈子交流表相关字段*/
    public static final String TABLE_COMMUNITY = "Community";               //圈子交流表
    public static final String COMMUNITY_NAME = "nikeName";                 //发表用户昵称
    public static final String COMMUNITY_USER_HEAD = "userHead";            //用户头像地址
    public static final String COMMUNITY_ID = "id";                         //id
    public static final String COMMUNITY_USER = "user";                     //发表者
    public static final String COMMUNITY_IMG_LIST = "imgList";              //图片
    public static final String COMMUNITY_CONTENT = "content";               //内容
    public static final String COMMUNITY_COLLECTION_COUNT = "collectionCount";    //收藏
    public static final String COMMUNITY_LIKE_COUNT = "likeCount";          //点赞数
    public static final String COMMUNITY_COMMENT_COUNT = "commentCount";    //评论数
    public static final String COMMUNITY_IS_DELETED = "isDelete";           //话题删除标记
    public static final String COMMUNITY_VIDEO_NAME = "videoName";          //视频的名称

    /*评论表相关字段*/
    public static final String TABLE_COMMENT = "Comment";                   //评论表
    public static final String COMMENT_COMMUNITY_ID = "community";          //圈子表外键(id)
    public static final String COMMENT_NAME = "name";                       //用户名称(id)
    public static final String COMMENT_USER = "user";                       //评论用户(userid)
    public static final String COMMENT_HEAD = "head";                       //评论用户头像
    public static final String COMMENT_CONTENT = "content";                 //评论表-评论内容
    public static final String COMMENT_DATE = "date";                       //时间
    public static final String COMMENT_HAS_DELETED = "isDelete";            //删除标记

    /*关注表相关字段*/
    public static final String TABLE_ATTENTION = "Attention";               //关注表
    public static final String ATTENTION_USER = "user";                     //关注用户
    public static final String ATTENTION_OBSERVED_USER = "observedUser";    //被观察用户
    public static final String ATTENTION_ISDELETE = "isDelete";             //删除标记

    /*点赞表相关字段*/
    public static final String TABLE_LIKE = "Like";                         //点赞表
    public static final String LIKE_COMMUNITY = "community";                //点赞圈子
    public static final String LIKE_USER = "user";                          //点赞用户

    /*消息表相关字段*/
    public static final String TABLE_MESSAGE = "Message";                   //消息表
    public static final String MESSAGE_USER = "user";                       //消息表--用户
    public static final String MESSAGE_CREATE_USER = "createUser";          //消息表--创建用户
    public static final String MESSAGE_COMMUNITY = "community";             //消息表--圈子话题
    public static final String MESSAGE_COMMENT = "comment";                 //消息表--评论
    public static final String MESSAGE_CONTENT = "content";                 //消息表--评论内容
    public static final String MESSAGE_TYPE = "type";                       //消息表--类型 1--评论

    /*收藏表相关字段*/
    public static final String TABLE_COLLECT = "Collect";                   //个人数据收藏
    public static final String COLLECT_USER = "user";                       //个人数据收藏 --收藏者
    public static final String COLLECT_TYPE = "type";                       //收藏 -- 类型
    public static final String COLLECT_TYPE_COMMUNITY = "community";             //收藏 -- 讨论
    public static final String COLLECT_TYPE_GOODS = "goods";                     //收藏 -- 商品

    /*书籍库表相关字段*/
    public static final String TABLE_BOOKS = "Book";                        //书籍库
    public static final String BOOKS_NAME = "name";                         //书名
    public static final String BOOKS_PRICE = "price";                       //价格
    public static final String BOOKS_PIC = "pic";                           //书籍库
    public static final String BOOKS_HOT = "hot";                           //热度
    public static final String BOOKS_COUNT_COLLECT = "collectCount";        //收藏数
    public static final String BOOKS_AUTHOR = "author";                     //作者
    public static final String BOOKS_PUBLISHING = "publishing";             //出版
    public static final String BOOKS_DEC = "description";                   //描述
    public static final String BOOKS_RELEASE_TIME = "releaseTime";          //出版时间

    /*书籍作者表相关字段*/
    public static final String TABLE_AUTHOR = "Author";                     //表名
    public static final String AUTHOR_NAME = "name";                        //作者名字
    public static final String AUTHOR_ICON = "icon";                        //作者头像
    public static final String AUTHOR_FANS_COUNT = "fansCount";             //粉丝数
    public static final String AUTHOR_ARTICLE_COUNT = "articleCount";       //文章数量


    /**
     * 发表圈子
     *
     * @param imgList
     * @param content
     * @param videoName
     */
    public static void publishCommunity(String imgList, String content, String videoName) {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            AVObject community = new AVObject(TABLE_COMMUNITY);
            community.put(COMMUNITY_USER, avUser.getObjectId());
            community.put(COMMUNITY_IMG_LIST, imgList);
            community.put(COMMUNITY_CONTENT, content);
            community.put(COMMUNITY_LIKE_COUNT, 0);
            community.put(COMMUNITY_COMMENT_COUNT, 0);
            community.put(COMMUNITY_IS_DELETED, 0);
            community.put(COMMUNITY_VIDEO_NAME, videoName);
        }
    }

    /**
     * 评论圈子
     *
     * @param communityId
     * @param content
     */
    public static void commentCommunity(String communityId, String content) {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            AVObject comment = new AVObject(TABLE_COMMENT);
            comment.put(COMMENT_COMMUNITY_ID, communityId);
            comment.put(COMMENT_USER, avUser.getObjectId());
            comment.put(COMMENT_CONTENT, content);
            comment.put(COMMENT_DATE, System.currentTimeMillis());
            comment.put(COMMENT_HAS_DELETED, 0);
        }
    }

    /**
     * 添加收藏
     */
    public static void addCollect(AVObject contentObj, String type, SaveCallback callback) {
        AVObject collect = AVObject.create(TABLE_COLLECT);
        if (type.equals(COLLECT_TYPE_COMMUNITY)) {
            collect.put(COLLECT_TYPE_COMMUNITY, contentObj);
        } else {
            collect.put(COLLECT_TYPE_GOODS, contentObj);
        }
        collect.put(COLLECT_TYPE, type);
        collect.put(COLLECT_USER, AVUser.getCurrentUser());
        collect.saveInBackground(callback);
    }

    /**
     * 修改昵称
     *
     * @param nick
     */
    public static void updateNickAndMale(String nick, String male) {
        AVUser user = AVUser.getCurrentUser();
        user.put(USER_NICKE_NAME, nick);
        user.put(USER_MALE, male);
        user.saveInBackground();
    }

    /**
     * 删除当前用户评论
     *
     * @param callback
     */
    public static void deleteComment(AVObject avObject, @NonNull DeleteCallback callback) {
        avObject.deleteInBackground(callback);
    }

    /**
     * 删除当前用户评论
     *
     * @param callback
     */
    public static void deleteMessage(AVObject avObject, @NonNull DeleteCallback callback) {
        avObject.deleteInBackground(callback);
    }

    /**
     * 消息列表
     *
     * @param callback
     */
    public static void requestMessageList(int page, int pagesize, @NonNull FindCallback<AVObject> callback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_MESSAGE);
        query.whereEqualTo(MESSAGE_USER, AVUser.getCurrentUser());
        query.include(MESSAGE_CREATE_USER);
        query.include(MESSAGE_USER);
        query.include(MESSAGE_COMMUNITY);
        query.include(MESSAGE_COMMUNITY + "." + COMMENT_USER);

        query.setLimit(pagesize);
        query.setSkip(page * pagesize);
        query.findInBackground(callback);
    }

    /**
     * 消息的数量
     *
     * @param countCallback
     */
    public static void requestMessageSize(CountCallback countCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_MESSAGE);
        query.whereEqualTo(AVDbManager.MESSAGE_USER, AVUser.getCurrentUser());
        query.countInBackground(countCallback);
    }

    public static void cancelCollect(AVObject contentObj, String type, final IColleactCallBack callBack) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_COLLECT);
        if (type.equals(COLLECT_TYPE_COMMUNITY)) {
            query.whereEqualTo(COLLECT_TYPE_COMMUNITY, contentObj);
        } else if (type.equals(COLLECT_TYPE_GOODS)) {
            query.whereEqualTo(COLLECT_TYPE_GOODS, contentObj);
        }

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null && list != null && list.size() > 0) {
                    list.get(0).deleteInBackground();
                    if (callBack != null) {
                        callBack.callback(true);
                    }
                } else {
                    if (callBack != null) {
                        callBack.callback(false);
                    }
                }
            }
        });
    }

    public interface IColleactCallBack {
        void callback(boolean isSuccess);
    }

    /**
     * 新旧密码修改
     *
     * @param oldPassword
     * @param newPassword
     * @param callBack
     */
    public static void changePassword(String oldPassword, final String newPassword, UpdatePasswordCallback callBack) {
        if (AVUser.getCurrentUser() == null) {
            ToastUtil.show("请先登录");
            return;
        }
        AVUser.getCurrentUser().updatePasswordInBackground(oldPassword, newPassword, callBack);
    }
}
