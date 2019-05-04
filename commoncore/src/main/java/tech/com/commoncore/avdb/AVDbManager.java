package tech.com.commoncore.avdb;

public class AVDbManager {

    /*用户表相关字段*/
    public static final String USER_NICK_NAME = "nickName";                //昵称
    public static final String USER_SIGN = "sign";                          //个性签名
    public static final String USER_HEAD_ICON = "icon";                     //头像

    /*多媒体文件表*/
    public static final String TABLE_MEDIA = "Media";                       //圈子交流表
    public static final String MEDIA_USER = "user";                         //发表者
    public static final String MEDIA_FILE = "file";                         //文件地址
    public static final String MEDIA_FILE_NAME = "fileName";                //文件别名
    public static final String MEDIA_FILE_TYPE = "fileType";                //文件类型
    public static final String MEDIA_TYPE_MUSIC = "music";                  //文件类型
    public static final String MEDIA_TYPE_VIDEO = "video";                  //文件类型
    public static final String MEDIA_CONTENT = "content";                   //内容
    public static final String MEDIA_LIKE_COUNT = "likeCount";              //点赞数
    public static final String MEDIA_COMMENT_COUNT = "commentCount";        //评论数
    public static final String MEDIA_IS_DELETED = "isDelete";               //话题删除标记

    /*评论表相关字段*/
    public static final String TABLE_COMMENT = "Comment";                   //评论表
    public static final String COMMENT_COMMUNITY_ID = "community";          //圈子表外键(id)
    public static final String COMMENT_NAME = "name";                       //用户名称(id)
    public static final String COMMENT_USER = "user";                       //评论用户(userid)
    public static final String COMMENT_HEAD = "head";                       //评论用户头像
    public static final String COMMENT_CONTENT = "content";                 //评论表-评论内容
    public static final String COMMENT_DATE = "date";                       //时间
    public static final String COMMENT_HAS_DELETED = "isDelete";            //删除标记

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

    public static final String TABLE_RESUME = "Resume";                     //简历表
    public static final String RESUME_NAME = "name";                         //名称
    public static final String RESUME_SEX = "sex";                           //性别
    public static final String RESUME_AGE = "age";                           //年龄
    public static final String RESUME_ADDRESS = "address";                   //地址
    public static final String RESUME_NUMBER = "number";                     //手机号
    public static final String RESUME_E_MAIL = "email";                      //邮箱
    public static final String RESUME_J_START = "jobStart";                  //工作状态
    public static final String RESUME_INTENTION = "intention";               //求职意向
    public static final String RESUME_J_ADDRESS = "jobAddress";              //工作地址
    public static final String RESUME_SALARY = "salary";                     //薪资
    public static final String RESUME_J_FLAG = "jobFlag";                    //个人表签 (技能项)

}
