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
    public static final String ATTENTION_DELETE_FLAG = "isDelete";             //删除标记

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

}
