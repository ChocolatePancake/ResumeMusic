package tech.com.commoncore.avdb;

public class AVDbManager {

    /*用户表相关字段*/
    public static final String USER_NICK_NAME = "nickName";                //昵称
    public static final String USER_SIGN = "sign";                          //个性签名
    public static final String USER_HEAD_ICON = "icon";                     //头像

    public static final String STATUS_TYPE_UNSTART = "unStatus";
    public static final String STATUS_TYPE_ING = "ing";
    public static final String STATUS_TYPE_END = "end";
    public static final String STATUS_TYPE_ABANDON = "abandon";

    public static final int GRADE_80_100 = 0;
    public static final int GRADE_100_150 = 1;
    public static final int GRADE_150_PULS = 2;

    public static final String TARGET_AVUSER = "targetAVUser";

    /*多媒体文件表*/
    public static final String TABLE_MEDIA = "Media";                       //媒体文件表
    public static final String MEDIA_USER = "user";                         //发表者
    public static final String MEDIA_FILE = "file";                         //文件地址
    public static final String MEDIA_GRADE = "grade";                       //文件等级
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

    /*简历表*/
    //基本信息
    public static final String TABLE_RESUME = "Resume";                      //简历表
    public static final String RESUME_USER = "user";                         //用户id
    public static final String RESUME_HEAD = "head";                         //头像
    public static final String RESUME_NAME = "name";                         //名称
    public static final String RESUME_SEX = "sex";                           //性别
    public static final String RESUME_AGE = "age";                           //年龄
    public static final String RESUME_HOME_ADDRESS = "homeAddress";          //地址
    public static final String RESUME_NUMBER = "number";                     //手机号
    public static final String RESUME_E_MAIL = "email";                      //邮箱
    public static final String RESUME_JOB_AGE = "jobAge";                    //工作年龄
    public static final String RESUME_JOB_STATUS = "jobStatus";              //工作状态
    public static final String RESUME_CARD_NUMBER = "cardNumber";            //证件号
    public static final String RESUME_NATIONALITY = "nationality";           //国籍/户口
    public static final String RESUME_MARRIAGE = "marriage";                 //婚姻状况
    //求职意向
    public static final String RESUME_INTENTION = "intention";               //求职意向
    public static final String RESUME_JOB_ADDRESS = "jobAddress";            //工作地址
    public static final String RESUME_SALARY = "salary";                     //薪资范围
    public static final String RESUME_JOB_FLAG = "jobFlag";                  //个人表签 (技能项)
    //教育经历
    public static final String RESUME_SCHOOL = "school";                     //毕业院校
    public static final String RESUME_DISCIPLINE = "discipline";             //大学专业
    public static final String RESUME_EDUCATION = "education";               //学历
    public static final String RESUME_SCHOOL_START_TIME = "schoolStartTime"; //开始时间
    public static final String RESUME_SCHOOL_END_TIME = "schoolEndTime";     //结束时间
    //工作经历
    public static final String RESUME_COMPANY = "company";                     //公司名称
    public static final String RESUME_POSITION = "position";                   //所处职位
    public static final String RESUME_JOB_START_TIME = "jobStartTime";         //开始时间
    public static final String RESUME_JOB_END_TIME = "jobEndTime";             //结束时间
    //项目经历
    public static final String RESUME_PROJECT_NAME = "projectName";             //项目名称
    public static final String RESUME_COMPANY_NAME = "companyName";             //开始时间
    public static final String RESUME_PROJECT_DESCRIPTION = "projectDescription";//项目描述
    public static final String RESUME_PROJECT_START_TIME = "projectStartTime";   //开始时间
    public static final String RESUME_PROJECT_END_TIME = "projectEndTime";       //结束时间

    public static final String TABLE_PARTY = "Party";                        //活动表
    public static final String PARTY_USER = "user";                          //用户id
    public static final String PARTY_TITLE = "title";                        //标题
    public static final String PARTY_CONTENT = "content";                    //内容
    public static final String PARTY_START_TIME = "startTime";               //开始时间
    public static final String PARTY_END_TIME = "endTime";                   //结束时间
    public static final String PARTY_ADDRESS = "address";                    //地点
    public static final String PARTY_PEOPLE = "people";                      //人数
    public static final String PARTY_STATUS = "status";                      //状态

    public static final String TABLE_PLAN = "Plan";                          //策划表
    public static final String PLAN_USER = "user";                           //用户
    public static final String PLAN_TYPE = "type";                           //类型
    public static final String PLAN_TITLE = "title";                         //标题
    public static final String PLAN_CONTENT = "content";                     //内容
    public static final String PLAN_START_TIME = "startTime";                //开始时间
    public static final String PLAN_END_TIME = "endTime";                    //结束时间
    public static final String PLAN_STATUS = "status";                       //状态

}
