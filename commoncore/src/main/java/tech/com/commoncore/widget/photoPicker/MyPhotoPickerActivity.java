/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.com.commoncore.widget.photoPicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aries.ui.view.title.TitleBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import cn.bingoogolapple.baseadapter.BGAGridDivider;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnNoDoubleClickListener;
import cn.bingoogolapple.photopicker.adapter.BGAPhotoPickerAdapter;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.model.BGAPhotoFolderModel;
import cn.bingoogolapple.photopicker.util.BGAAsyncTask;
import cn.bingoogolapple.photopicker.util.BGALoadPhotoTask;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import tech.com.commoncore.R;
import tech.com.commoncore.base.BaseTitleActivity;

/**
 * Author:ChenPengbo
 * Time:2018-10-20
 * Desc:图片选择界面(参考BGAPhotoPickerActivity)
 */
public class MyPhotoPickerActivity extends BaseTitleActivity implements BGAOnItemChildClickListener, BGAAsyncTask.Callback<ArrayList<BGAPhotoFolderModel>> {
    private static final String EXTRA_CAMERA_FILE_DIR = "EXTRA_CAMERA_FILE_DIR";
    private static final String EXTRA_SELECTED_PHOTOS = "EXTRA_SELECTED_PHOTOS";
    private static final String EXTRA_MAX_CHOOSE_COUNT = "EXTRA_MAX_CHOOSE_COUNT";
    private static final String EXTRA_PAUSE_ON_SCROLL = "EXTRA_PAUSE_ON_SCROLL";

    private static final String STATE_SELECTED_PHOTOS = "STATE_SELECTED_PHOTOS";

    private RecyclerView mContentRv;

    /**
     * 拍照的请求码
     */
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    /**
     * 预览照片的请求码
     */
    private static final int RC_PREVIEW = 2;

    private static final int SPAN_COUNT = 3;

    private BGAPhotoFolderModel mCurrentPhotoFolderModel;

    /**
     * 是否可以拍照
     */
    private boolean mTakePhotoEnabled;
    /**
     * 最多选择多少张图片，默认等于1，为单选
     */
    private int mMaxChooseCount = 1;
    /**
     * 图片目录数据集合
     */
    private ArrayList<BGAPhotoFolderModel> mPhotoFolderModels;

    private BGAPhotoPickerAdapter mPicAdapter;

    private BGAPhotoHelper mPhotoHelper;

    //    private BGAPhotoFolderPw mPhotoFolderPw;
    private MyPhotoFolderPw mPhotoFolderPw;

    private BGALoadPhotoTask mLoadPhotoTask;
    private AppCompatDialog mLoadingDialog;

    private TextView centerTitle;

    public static class IntentBuilder {
        private Intent mIntent;

        public IntentBuilder(Context context) {
            mIntent = new Intent(context, MyPhotoPickerActivity.class);
        }

        /**
         * 拍照后图片保存的目录。如果传 null 表示没有拍照功能，如果不为 null 则具有拍照功能，
         */
        public IntentBuilder cameraFileDir(@Nullable File cameraFileDir) {
            mIntent.putExtra(EXTRA_CAMERA_FILE_DIR, cameraFileDir);
            return this;
        }

        /**
         * 图片选择张数的最大值
         *
         * @param maxChooseCount
         * @return
         */
        public IntentBuilder maxChooseCount(int maxChooseCount) {
            mIntent.putExtra(EXTRA_MAX_CHOOSE_COUNT, maxChooseCount);
            return this;
        }

        /**
         * 当前已选中的图片路径集合，可以传 null
         */
        public IntentBuilder selectedPhotos(@Nullable ArrayList<String> selectedPhotos) {
            mIntent.putStringArrayListExtra(EXTRA_SELECTED_PHOTOS, selectedPhotos);
            return this;
        }

        /**
         * 滚动列表时是否暂停加载图片，默认为 false
         */
        public IntentBuilder pauseOnScroll(boolean pauseOnScroll) {
            mIntent.putExtra(EXTRA_PAUSE_ON_SCROLL, pauseOnScroll);
            return this;
        }

        public Intent build() {
            return mIntent;
        }
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setOnCenterClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mPhotoFolderModels != null && mPhotoFolderModels.size() > 0) {
                    showPhotoFolderPw();
                }
            }
        });
        titleBar.setOnRightTextClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                returnSelectedPhotos(mPicAdapter.getSelectedPhotos());
            }
        });
        titleBar.setTextColor(Color.WHITE);
        titleBar.setBgColor(Color.GRAY);
        titleBar.setRightText(R.string.determine);
        titleBar.setStatusBarLightMode(false);
        titleBar.setLeftTextDrawable(R.mipmap.back_white);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_photo_picker;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mContentRv = findViewById(R.id.rv_photo_picker_content);
        centerTitle = mTitleBar.getTextView(Gravity.CENTER);

        ViewGroup.LayoutParams layoutParams = centerTitle.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        centerTitle.setLayoutParams(layoutParams);
        setCenterTitleImage(centerTitle, R.drawable.ic_arrow_down);

        if (mCurrentPhotoFolderModel != null) {
            centerTitle.setText(mCurrentPhotoFolderModel.name);
        } else {
            centerTitle.setText(R.string.bga_pp_all_image);
        }

        mPicAdapter = new BGAPhotoPickerAdapter(mContentRv);
        mPicAdapter.setOnItemChildClickListener(this);

        if (getIntent().getBooleanExtra(EXTRA_PAUSE_ON_SCROLL, false)) {
            mContentRv.addOnScrollListener(new BGARVOnScrollListener(this));
        }

        // 获取拍照图片保存目录
        File cameraFileDir = (File) getIntent().getSerializableExtra(EXTRA_CAMERA_FILE_DIR);
        if (cameraFileDir != null) {
            mTakePhotoEnabled = true;
            mPhotoHelper = new BGAPhotoHelper(cameraFileDir);
        }
        // 获取图片选择的最大张数
        mMaxChooseCount = getIntent().getIntExtra(EXTRA_MAX_CHOOSE_COUNT, 1);
        if (mMaxChooseCount < 1) {
            mMaxChooseCount = 1;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        mContentRv.setLayoutManager(layoutManager);
        mContentRv.addItemDecoration(BGAGridDivider.newInstanceWithSpaceRes(R.dimen.bga_pp_size_photo_divider));

        ArrayList<String> selectedPhotos = getIntent().getStringArrayListExtra(EXTRA_SELECTED_PHOTOS);
        if (selectedPhotos != null && selectedPhotos.size() > mMaxChooseCount) {
            String selectedPhoto = selectedPhotos.get(0);
            selectedPhotos.clear();
            selectedPhotos.add(selectedPhoto);
        }

        mContentRv.setAdapter(mPicAdapter);
        mPicAdapter.setSelectedPhotos(selectedPhotos);

        renderTopRightBtn();
    }

    private void setCenterTitleImage(TextView view, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        int width = (int) getResources().getDimension(R.dimen.dp_26);
        int height = (int) getResources().getDimension(R.dimen.dp_26);
        drawable.setBounds(0, 0, width, height);
        view.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 获取已选择的图片集合
     *
     * @param intent
     * @return
     */
    public static ArrayList<String> getSelectedPhotos(Intent intent) {
        return intent.getStringArrayListExtra(EXTRA_SELECTED_PHOTOS);
    }


    @Override
    protected void onStart() {
        super.onStart();
        showLoadingDialog();
        mLoadPhotoTask = new BGALoadPhotoTask(this, this, mTakePhotoEnabled).perform();
    }

    private void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AppCompatDialog(this);
            mLoadingDialog.setContentView(R.layout.bga_pp_dialog_loading);
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    /**
     * 返回已选中的图片集合
     *
     * @param selectedPhotos
     */
    private void returnSelectedPhotos(ArrayList<String> selectedPhotos) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EXTRA_SELECTED_PHOTOS, selectedPhotos);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showPhotoFolderPw() {
        if (mPhotoFolderPw == null) {
            mPhotoFolderPw = new MyPhotoFolderPw(this, mTitleBar, new MyPhotoFolderPw.Delegate() {
                @Override
                public void onSelectedFolder(int position) {
                    reloadPhotos(position);
                }

                @Override
                public void executeDismissAnim() {
                    setCenterTitleImage(centerTitle, R.drawable.ic_arrow_down);
                }
            });
        }
        mPhotoFolderPw.setData(mPhotoFolderModels);
        mPhotoFolderPw.show();
        setCenterTitleImage(centerTitle, R.drawable.ic_arrow_up);
    }

    /**
     * 显示只能选择 mMaxChooseCount 张图的提示
     */
    private void toastMaxCountTip() {
        BGAPhotoPickerUtil.show(getString(R.string.bga_pp_toast_photo_picker_max, mMaxChooseCount));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                ArrayList<String> photos = new ArrayList<>(Arrays.asList(mPhotoHelper.getCameraFilePath()));

                Intent photoPickerPreview = new MyPhotoPickerPreviewActivity.IntentBuilder(this)
                        .isFromTakePhoto(true)
                        .maxChooseCount(1)
                        .previewPhotos(photos)
                        .selectedPhotos(photos)
                        .currentPosition(0)
                        .build();
                startActivityForResult(photoPickerPreview, RC_PREVIEW);
            } else if (requestCode == RC_PREVIEW) {
                if (MyPhotoPickerPreviewActivity.getIsFromTakePhoto(data)) {
                    // 从拍照预览界面返回，刷新图库
                    mPhotoHelper.refreshGallery();
                }
                returnSelectedPhotos(MyPhotoPickerPreviewActivity.getSelectedPhotos(data));
            }
        } else if (resultCode == RESULT_CANCELED && requestCode == RC_PREVIEW) {
            if (MyPhotoPickerPreviewActivity.getIsFromTakePhoto(data)) {
                // 从拍照预览界面返回，删除之前拍的照片
                mPhotoHelper.deleteCameraFile();
            } else {
                mPicAdapter.setSelectedPhotos(MyPhotoPickerPreviewActivity.getSelectedPhotos(data));
                renderTopRightBtn();
            }
        }
    }

    /**
     * 渲染右上角按钮
     */
    private void renderTopRightBtn() {
        if (mPicAdapter.getSelectedCount() == 0) {
            mTitleBar.setRightText(R.string.determine);
            //            mSubmitTv.setEnabled(false);
            //            mSubmitTv.setText(mTopRightBtnText);
        } else {
            mTitleBar.setRightText(getString(R.string.determine) + "(" + mPicAdapter.getSelectedCount() + "/" + mMaxChooseCount + ")");
            //            mSubmitTv.setEnabled(true);
            //            mSubmitTv.setText(mTopRightBtnText);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BGAPhotoHelper.onSaveInstanceState(mPhotoHelper, outState);
        outState.putStringArrayList(STATE_SELECTED_PHOTOS, mPicAdapter.getSelectedPhotos());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BGAPhotoHelper.onRestoreInstanceState(mPhotoHelper, savedInstanceState);
        mPicAdapter.setSelectedPhotos(savedInstanceState.getStringArrayList(STATE_SELECTED_PHOTOS));
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int position) {
        if (view.getId() == R.id.iv_item_photo_camera_camera) {
            handleTakePhoto();
        } else if (view.getId() == R.id.iv_item_photo_picker_photo) {
            changeToPreview(position);
        } else if (view.getId() == R.id.iv_item_photo_picker_flag) {
            handleClickSelectFlagIv(position);
        }
    }

    /**
     * 处理拍照
     */
    private void handleTakePhoto() {
        if (mMaxChooseCount == 1) {
            // 单选
            takePhoto();
        } else if (mPicAdapter.getSelectedCount() == mMaxChooseCount) {
            toastMaxCountTip();
        } else {
            takePhoto();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        try {
            startActivityForResult(mPhotoHelper.getTakePhotoIntent(), REQUEST_CODE_TAKE_PHOTO);
        } catch (Exception e) {
            BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_take_photo);
        }
    }

    /**
     * 跳转到图片选择预览界面
     *
     * @param position 当前点击的item的索引位置
     */
    private void changeToPreview(int position) {
        int currentPosition = position;
        if (mCurrentPhotoFolderModel.isTakePhotoEnabled()) {
            currentPosition--;
        }
        Intent photoPickerPreviewIntent = new MyPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos((ArrayList<String>) mPicAdapter.getData())
                .selectedPhotos(mPicAdapter.getSelectedPhotos())
                .maxChooseCount(mMaxChooseCount)
                .currentPosition(currentPosition)
                .isFromTakePhoto(false)
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PREVIEW);
    }

    /**
     * 处理点击选择按钮事件
     *
     * @param position 当前点击的item的索引位置
     */
    private void handleClickSelectFlagIv(int position) {
        String currentPhoto = mPicAdapter.getItem(position);
        if (mMaxChooseCount == 1) {
            // 单选
            if (mPicAdapter.getSelectedCount() > 0) {
                String selectedPhoto = mPicAdapter.getSelectedPhotos().remove(0);
                if (TextUtils.equals(selectedPhoto, currentPhoto)) {
                    mPicAdapter.notifyItemChanged(position);
                } else {
                    int preSelectedPhotoPosition = mPicAdapter.getData().indexOf(selectedPhoto);
                    mPicAdapter.notifyItemChanged(preSelectedPhotoPosition);
                    mPicAdapter.getSelectedPhotos().add(currentPhoto);
                    mPicAdapter.notifyItemChanged(position);
                }
            } else {
                mPicAdapter.getSelectedPhotos().add(currentPhoto);
                mPicAdapter.notifyItemChanged(position);
            }
            renderTopRightBtn();
        } else {
            // 多选
            if (!mPicAdapter.getSelectedPhotos().contains(currentPhoto) && mPicAdapter.getSelectedCount() == mMaxChooseCount) {
                toastMaxCountTip();
            } else {
                if (mPicAdapter.getSelectedPhotos().contains(currentPhoto)) {
                    mPicAdapter.getSelectedPhotos().remove(currentPhoto);
                } else {
                    mPicAdapter.getSelectedPhotos().add(currentPhoto);
                }
                mPicAdapter.notifyItemChanged(position);

                renderTopRightBtn();
            }
        }
    }

    private void reloadPhotos(int position) {
        if (position < mPhotoFolderModels.size()) {
            mCurrentPhotoFolderModel = mPhotoFolderModels.get(position);
            centerTitle.setText(mCurrentPhotoFolderModel.name);

            mPicAdapter.setPhotoFolderModel(mCurrentPhotoFolderModel);
        }
    }

    @Override
    public void onPostExecute(ArrayList<BGAPhotoFolderModel> photoFolderModels) {
        dismissLoadingDialog();
        mLoadPhotoTask = null;
        mPhotoFolderModels = photoFolderModels;
        reloadPhotos(mPhotoFolderPw == null ? 0 : mPhotoFolderPw.getCurrentPosition());
    }

    @Override
    public void onTaskCancelled() {
        dismissLoadingDialog();
        mLoadPhotoTask = null;
    }

    private void cancelLoadPhotoTask() {
        if (mLoadPhotoTask != null) {
            mLoadPhotoTask.cancelTask();
            mLoadPhotoTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        cancelLoadPhotoTask();
        super.onDestroy();
    }
}