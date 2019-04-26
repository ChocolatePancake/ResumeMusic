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
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aries.ui.view.title.TitleBarView;

import java.util.ArrayList;

import cn.bingoogolapple.baseadapter.BGAOnNoDoubleClickListener;
import cn.bingoogolapple.photopicker.adapter.BGAPhotoPageAdapter;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import cn.bingoogolapple.photopicker.widget.BGAHackyViewPager;
import tech.com.commoncore.R;
import tech.com.commoncore.base.BaseTitleActivity;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author:ChenPengbo
 * Time:2018-10-20
 * Desc:图片选择预览界面(参考BGAPhotoPickerPreviewActivity)
 */
public class MyPhotoPickerPreviewActivity extends BaseTitleActivity implements PhotoViewAttacher.OnViewTapListener {

    BGAHackyViewPager mContentHvp;
    TextView mChooseTv;
    RelativeLayout mChooseRl;

    private static final String EXTRA_PREVIEW_PHOTOS = "EXTRA_PREVIEW_PHOTOS";
    private static final String EXTRA_SELECTED_PHOTOS = "EXTRA_SELECTED_PHOTOS";
    private static final String EXTRA_MAX_CHOOSE_COUNT = "EXTRA_MAX_CHOOSE_COUNT";
    private static final String EXTRA_CURRENT_POSITION = "EXTRA_CURRENT_POSITION";
    private static final String EXTRA_IS_FROM_TAKE_PHOTO = "EXTRA_IS_FROM_TAKE_PHOTO";

    private ArrayList<String> mSelectedPhotos;
    private BGAPhotoPageAdapter mPhotoPageAdapter;
    private int mMaxChooseCount = 1;

    private boolean mIsHidden = false;
    /**
     * 上一次标题栏显示或隐藏的时间戳
     */
    private long mLastShowHiddenTime;
    /**
     * 是否是拍完照后跳转过来
     */
    private boolean mIsFromTakePhoto;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("")
                .setOnRightTextClickListener(new BGAOnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra(EXTRA_SELECTED_PHOTOS, mSelectedPhotos);
                        intent.putExtra(EXTRA_IS_FROM_TAKE_PHOTO, mIsFromTakePhoto);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).setRightText(R.string.determine)
                .setStatusBarLightMode(false);
        titleBar.setTextColor(Color.WHITE);
        titleBar.setBgColor(Color.GRAY);
        titleBar.setLeftTextDrawable(R.mipmap.back_white);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_photo_picker_preview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mContentHvp = findViewById(R.id.hvp_photo_picker_preview_content);
        mChooseTv = findViewById(R.id.tv_photo_picker_preview_choose);
        mChooseRl = findViewById(R.id.rl_photo_picker_preview_choose);


        mChooseTv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String currentPhoto = mPhotoPageAdapter.getItem(mContentHvp.getCurrentItem());
                if (mSelectedPhotos.contains(currentPhoto)) {
                    mSelectedPhotos.remove(currentPhoto);
                    mChooseTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.bga_pp_ic_cb_normal, 0, 0, 0);
                    renderTopRightBtn();
                } else {
                    if (mMaxChooseCount == 1) {
                        // 单选

                        mSelectedPhotos.clear();
                        mSelectedPhotos.add(currentPhoto);
                        mChooseTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.bga_pp_ic_cb_checked, 0, 0, 0);
                        renderTopRightBtn();
                    } else {
                        // 多选

                        if (mMaxChooseCount == mSelectedPhotos.size()) {
                            BGAPhotoPickerUtil.show(getString(R.string.bga_pp_toast_photo_picker_max, mMaxChooseCount));
                        } else {
                            mSelectedPhotos.add(currentPhoto);
                            mChooseTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.bga_pp_ic_cb_checked, 0, 0, 0);
                            renderTopRightBtn();
                        }
                    }
                }
            }
        });

        mContentHvp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                handlePageSelectedStatus();
            }
        });

        // 获取图片选择的最大张数
        mMaxChooseCount = getIntent().getIntExtra(EXTRA_MAX_CHOOSE_COUNT, 1);
        if (mMaxChooseCount < 1) {
            mMaxChooseCount = 1;
        }

        mSelectedPhotos = getIntent().getStringArrayListExtra(EXTRA_SELECTED_PHOTOS);
        if (mSelectedPhotos == null) {
            mSelectedPhotos = new ArrayList<>();
        }

        ArrayList<String> previewPhotos = getIntent().getStringArrayListExtra(EXTRA_PREVIEW_PHOTOS);
        if (TextUtils.isEmpty(previewPhotos.get(0))) {
            // 从BGAPhotoPickerActivity跳转过来时，如果有开启拍照功能，则第0项为""
            previewPhotos.remove(0);
        }

        // 处理是否是拍完照后跳转过来
        mIsFromTakePhoto = getIntent().getBooleanExtra(EXTRA_IS_FROM_TAKE_PHOTO, false);
        if (mIsFromTakePhoto) {
            // 如果是拍完照后跳转过来，一直隐藏底部选择栏
            mChooseRl.setVisibility(View.INVISIBLE);
        }
        int currentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);

        mPhotoPageAdapter = new BGAPhotoPageAdapter(this, previewPhotos);
        mContentHvp.setAdapter(mPhotoPageAdapter);
        mContentHvp.setCurrentItem(currentPosition);

        renderTopRightBtn();
        handlePageSelectedStatus();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EXTRA_SELECTED_PHOTOS, mSelectedPhotos);
        intent.putExtra(EXTRA_IS_FROM_TAKE_PHOTO, mIsFromTakePhoto);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void handlePageSelectedStatus() {
        if (mPhotoPageAdapter == null) {
            return;
        }

        if (mSelectedPhotos.contains(mPhotoPageAdapter.getItem(mContentHvp.getCurrentItem()))) {
            mChooseTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.bga_pp_ic_cb_checked, 0, 0, 0);
        } else {
            mChooseTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.bga_pp_ic_cb_normal, 0, 0, 0);
        }
    }

    /**
     * 渲染右上角按钮
     */
    private void renderTopRightBtn() {
        if (mIsFromTakePhoto) {
            mTitleBar.setRight(R.string.determine);
//            mSubmitTv.setEnabled(true);
//            mSubmitTv.setText(mTopRightBtnText);
        } else if (mSelectedPhotos.size() == 0) {
            mTitleBar.setRightText("");
//            mSubmitTv.setEnabled(false);
//            mSubmitTv.setText(mTopRightBtnText);
        } else {
            mTitleBar.setRightText(getString(R.string.determine) + "(" + mSelectedPhotos.size() + "/" + mMaxChooseCount + ")");
//            mSubmitTv.setEnabled(true);
//            mSubmitTv.setText(mTopRightBtnText + "(" + mSelectedPhotos.size() + "/" + mMaxChooseCount + ")");
        }
    }


    @Override
    public void onViewTap(View view, float x, float y) {
        if (System.currentTimeMillis() - mLastShowHiddenTime > 500) {
            mLastShowHiddenTime = System.currentTimeMillis();
            if (mIsHidden) {
                showTitleBarAndChooseBar();
            } else {
                hiddenToolBarAndChooseBar();
            }
        }
    }

    private void showTitleBarAndChooseBar() {
        if (!mIsFromTakePhoto && mChooseRl != null) {
            mChooseRl.setVisibility(View.VISIBLE);
            ViewCompat.setAlpha(mChooseRl, 0);
            ViewCompat.animate(mChooseRl).alpha(1).setInterpolator(new DecelerateInterpolator(2)).start();
        }
    }

    private void hiddenToolBarAndChooseBar() {
        if (!mIsFromTakePhoto) {
            if (mChooseRl != null) {
                ViewCompat.animate(mChooseRl).alpha(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        }
    }

    public static class IntentBuilder {
        private Intent mIntent;

        public IntentBuilder(Context context) {
            mIntent = new Intent(context, MyPhotoPickerPreviewActivity.class);
        }

        /**
         * 图片选择张数的最大值
         */
        public IntentBuilder maxChooseCount(int maxChooseCount) {
            mIntent.putExtra(EXTRA_MAX_CHOOSE_COUNT, maxChooseCount);
            return this;
        }

        /**
         * 当前已选中的图片路径集合
         */
        public IntentBuilder selectedPhotos(ArrayList<String> selectedPhotos) {
            mIntent.putStringArrayListExtra(EXTRA_SELECTED_PHOTOS, selectedPhotos);
            return this;
        }

        /**
         * 当前预览的图片路径集合
         */
        public IntentBuilder previewPhotos(ArrayList<String> previewPhotos) {
            mIntent.putStringArrayListExtra(EXTRA_PREVIEW_PHOTOS, previewPhotos);
            return this;
        }

        /**
         * 当前预览图片的索引
         */
        public IntentBuilder currentPosition(int currentPosition) {
            mIntent.putExtra(EXTRA_CURRENT_POSITION, currentPosition);
            return this;
        }

        /**
         * 是否是拍完照后跳转过来
         */
        public IntentBuilder isFromTakePhoto(boolean isFromTakePhoto) {
            mIntent.putExtra(EXTRA_IS_FROM_TAKE_PHOTO, isFromTakePhoto);
            return this;
        }

        public Intent build() {
            return mIntent;
        }
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

    /**
     * 是否是拍照预览
     *
     * @param intent
     * @return
     */
    public static boolean getIsFromTakePhoto(Intent intent) {
        return intent.getBooleanExtra(EXTRA_IS_FROM_TAKE_PHOTO, false);
    }
}