package tech.com.commoncore.avdb;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Arrays;

import tech.com.commoncore.plog;

import static tech.com.commoncore.avdb.AVDbManager.*;

public class AVDbImpl implements AVDb {
    @Override
    public void publishCommunity(String imgList, String content, String videoName, @NonNull SaveCallback saveCallback) {
        plog.paly("到了处理的地方");
        if (AVUser.getCurrentUser() != null) {
            AVObject community = new AVObject(TABLE_COMMUNITY);
            community.put(COMMUNITY_USER, AVUser.getCurrentUser().getObjectId());
            community.put(COMMUNITY_NAME, AVUser.getCurrentUser().get(USER_NICKE_NAME));
            community.put(COMMUNITY_USER_HEAD, AVUser.getCurrentUser().get(USER_HEAD_ICON));
            community.put(COMMUNITY_IMG_LIST, imgList);
            community.put(COMMUNITY_CONTENT, content);
            community.put(COMMUNITY_COLLECTION_COUNT, 0);
            community.put(COMMUNITY_LIKE_COUNT, 0);
            community.put(COMMUNITY_COMMENT_COUNT, 0);
            community.put(COMMUNITY_IS_DELETED, 0);
            community.put(COMMUNITY_VIDEO_NAME, videoName);
            community.saveInBackground(saveCallback);
        }
    }

    @Override
    public void deleteCommunity(String communityId, @NonNull DeleteCallback callback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject todo = AVObject.createWithoutData(TABLE_COMMUNITY, communityId);
            todo.deleteInBackground(callback);
        }
    }

    @Override
    public void requestCommunity(String[] usersId, @NonNull FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_COMMUNITY);
        if (usersId != null && usersId.length > 0) {
            plog.paly(usersId.length + "");
            query.whereContainsAll(COMMUNITY_USER, Arrays.asList(usersId));
        }
        query.findInBackground(findCallback);
    }

    @Override
    public void publishComment(String communityId, String content, @NonNull SaveCallback saveCallback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject comment = new AVObject(TABLE_COMMENT);
            comment.put(COMMENT_COMMUNITY_ID, communityId);
            comment.put(COMMENT_USER, AVUser.getCurrentUser().getObjectId());
            comment.put(COMMENT_CONTENT, content);
            comment.put(COMMENT_NAME, AVUser.getCurrentUser().get(USER_NICKE_NAME));
            comment.put(COMMENT_DATE, System.currentTimeMillis());
            comment.put(COMMENT_HEAD, AVUser.getCurrentUser().get(USER_HEAD_ICON));
            comment.put(COMMENT_HAS_DELETED, 0);
            comment.saveInBackground(saveCallback);
            handlerCommentCount(communityId, true);
        }
    }

    @Override
    public void deleteComment(String commentId, @NonNull DeleteCallback callback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject todo = AVObject.createWithoutData(TABLE_COMMENT, commentId);
            todo.deleteInBackground(callback);
//            handlerCommentCount(, false);
        }
    }

    @Override
    public void requestComment(String communityId, @NonNull FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_COMMENT);
        query.whereEqualTo(COMMENT_COMMUNITY_ID, communityId);
        query.findInBackground(findCallback);
    }

    private void handlerCommentCount(String communityId, boolean isAdd) {
        AVObject todo = AVObject.createWithoutData(TABLE_COMMUNITY, communityId);
        int index = 1;
        if (!isAdd)
            index = -1;
        todo.increment(COMMUNITY_COMMENT_COUNT, index);
        todo.saveInBackground();
    }

    @Override
    public void addCollect(String contentId, String type, SaveCallback callback) {
        AVObject collect = new AVObject(TABLE_COLLECT);
        if (type.equals(COLLECT_TYPE_COMMUNITY)) {
            collect.put(COLLECT_TYPE_COMMUNITY, contentId);
        } else {
            collect.put(COLLECT_TYPE_GOODS, contentId);
        }
        collect.put(COLLECT_TYPE, type);
        collect.put(COLLECT_USER, AVUser.getCurrentUser());
        collect.saveInBackground(callback);
        handlerCollectionCount(contentId, true);
    }

    @Override
    public void addCollect(AVObject object, @NonNull SaveCallback callback) {
        if (object == null) {
            return;
        }
        object.saveInBackground(callback);
        if (String.valueOf(object.get(COLLECT_TYPE)).equals(COLLECT_TYPE_COMMUNITY)) {
            handlerCollectionCount((String) object.get(COLLECT_TYPE_COMMUNITY), true);
        } else {
            handlerCollectionCount((String) object.get(COLLECT_TYPE_GOODS), true);
        }
    }

    @Override
    public void deleteCollect(String collectId, String contentId, @NonNull DeleteCallback callback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject todo = AVObject.createWithoutData(TABLE_COLLECT, collectId);
            todo.deleteInBackground(callback);
            handlerCollectionCount(contentId, false);
        }
    }

    @Override
    public void requestCollect(@NonNull FindCallback<AVObject> findCallback) {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            AVQuery<AVObject> query = new AVQuery<>(TABLE_COLLECT);
            query.whereEqualTo(COLLECT_USER, AVUser.getCurrentUser().getObjectId());
            query.findInBackground(findCallback);
        }
    }

    private void handlerCollectionCount(String communityId, boolean isAdd) {
        AVObject todo = AVObject.createWithoutData(TABLE_COMMUNITY, communityId);
        int index = 1;
        if (!isAdd)
            index = -1;
        todo.increment(COMMUNITY_COLLECTION_COUNT, index);
        todo.saveInBackground();
    }

    @Override
    public void addAttention(String obUser, @NonNull SaveCallback callback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject attention = new AVObject(TABLE_ATTENTION);
            attention.put(ATTENTION_USER, AVUser.getCurrentUser().getObjectId());
            attention.put(ATTENTION_OBSERVED_USER, obUser);
            attention.put(ATTENTION_ISDELETE, 0);
            attention.saveInBackground(callback);
        }
    }

    @Override
    public void addAttention(AVObject object, @NonNull SaveCallback callback) {
        object.saveInBackground(callback);
    }

    @Override
    public void deleteAttention(String attentionId, @NonNull DeleteCallback callback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject todo = AVObject.createWithoutData(TABLE_ATTENTION, attentionId);
            todo.deleteInBackground(callback);
        }
    }

    @Override
    public void requestAttentionList(@NonNull FindCallback<AVObject> findCallback) {
        if (AVUser.getCurrentUser() != null) {
            AVQuery<AVObject> query = new AVQuery<>(TABLE_ATTENTION);
            query.whereEqualTo(ATTENTION_USER, AVUser.getCurrentUser().getObjectId());
            query.findInBackground(findCallback);
        }
    }

    @Override
    public void addLike(String community, SaveCallback callback) {
        if (AVUser.getCurrentUser() != null) {
            AVObject like = new AVObject(TABLE_LIKE);
            like.put(LIKE_COMMUNITY, community);
            like.put(LIKE_USER, AVUser.getCurrentUser().getObjectId());
            like.saveInBackground(callback);
            handlerLikeCount(community, true);
        }
    }

    @Override
    public void addLike(AVObject avObject, SaveCallback callback) {
        avObject.saveInBackground(callback);
        handlerLikeCount((String) avObject.get(LIKE_COMMUNITY), true);
    }

    private void handlerLikeCount(String communityId, boolean isAdd) {
        AVObject todo = AVObject.createWithoutData(TABLE_COMMUNITY, communityId);
        int index = 1;
        if (!isAdd)
            index = -1;
        todo.increment(COMMUNITY_LIKE_COUNT, index);
        todo.saveInBackground();
    }

    @Override
    public void requestLikeByCommunity(String community, @NonNull FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<>(TABLE_LIKE);
        query.whereEqualTo(LIKE_COMMUNITY, community);
        query.findInBackground(findCallback);
    }

    @Override
    public void requestLikeByUser(@NonNull FindCallback<AVObject> findCallback) {
        if (AVUser.getCurrentUser() != null) {
            AVQuery<AVObject> query = new AVQuery<>(TABLE_LIKE);
            query.whereEqualTo(LIKE_USER, AVUser.getCurrentUser().getObjectId());
            query.findInBackground(findCallback);
        }
    }
}
