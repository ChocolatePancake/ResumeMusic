package tech.com.commoncore.avdb;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

public interface AVDb {

    /**
     * 发表圈子
     */
    void publishCommunity(String imgList, String content, String videoName, @NonNull SaveCallback saveCallback);

    /**
     * 删除圈子
     */
    void deleteCommunity(String communityId, @NonNull DeleteCallback callback);

    /**
     * 查询圈子
     */
    void requestCommunity(String[] usersId, @NonNull FindCallback<AVObject> findCallback);

    /**
     * 评论圈子
     */
    void publishComment(String communityId, String content, @NonNull SaveCallback saveCallback);

    /**
     * 删除评论
     */
    void deleteComment(String commentId, @NonNull DeleteCallback callback);

    /**
     * 查询评论
     */
    void requestComment(String communityId, @NonNull FindCallback<AVObject> findCallback);

    /**
     * 添加收藏
     */
    void addCollect(String contentId, String type, @NonNull SaveCallback callback);

    void addCollect(AVObject object, @NonNull SaveCallback callback);

    /**
     * 删除收藏
     */
    void deleteCollect(String collectId, String contentId, @NonNull DeleteCallback callback);

    /**
     * 查询收藏
     */
    void requestCollect(@NonNull FindCallback<AVObject> findCallback);

    /**
     * 加关注
     */
    void addAttention(String obUser, @NonNull SaveCallback callback);

    void addAttention(AVObject object, @NonNull SaveCallback callback);

    /**
     * 取消关注
     */
    void deleteAttention(String attentionId, @NonNull DeleteCallback callback);

    /**
     * 查询我的关注
     */
    void requestAttentionList(@NonNull FindCallback<AVObject> findCallback);

    /**
     * 点赞
     */
    void addLike(String community, SaveCallback callback);

    void addLike(AVObject avObject, SaveCallback callback);

    /**
     * 获取点赞
     */
    void requestLikeByCommunity(String community, @NonNull FindCallback<AVObject> findCallback);

    void requestLikeByUser(@NonNull FindCallback<AVObject> findCallback);
}
