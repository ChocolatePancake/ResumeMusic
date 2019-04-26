package tech.com.commoncore.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.util.Size;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.logging.Logger;

import tech.com.commoncore.utils.SizeUtil;

public class ImageGetterUtils {

    public static MyImageGetter getImageGetter(Context context, TextView textView) {
        MyImageGetter myImageGetter = new MyImageGetter(context, textView);
        return myImageGetter;
    }

    public static class MyImageGetter implements Html.ImageGetter {

        private URLDrawable urlDrawable = null;
        private TextView textView;
        private Context context;

        public MyImageGetter(Context context, TextView textView) {
            this.textView = textView;
            this.context = context;
        }

        @Override
        public Drawable getDrawable(final String source) {
            urlDrawable = new URLDrawable();
            Glide.with(context).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    urlDrawable.bitmap = changeBitmapSize(resource);
                    urlDrawable.setBounds(0, 0, resource.getWidth(), changeBitmapSize(resource).getHeight());

                    textView.invalidate();
                    textView.setText(textView.getText());//不加这句显示不出来图片，原因不详
                }
            });

            return urlDrawable;
        }

        public class URLDrawable extends BitmapDrawable {
            public Bitmap bitmap;

            @Override
            public void draw(Canvas canvas) {
                super.draw(canvas);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
                }
            }
        }

        private Bitmap changeBitmapSize(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int newWidth = bitmap.getWidth();
            int newHeight = bitmap.getHeight();
            //设置想要的大小
            //计算压缩的比率
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            //获取想要缩放的matrix
            Matrix matrix = new Matrix();
            if (newWidth > SizeUtil.getScreenWidth()) {
                float s = (float) SizeUtil.getScreenWidth() / (float) newWidth;
                matrix.postScale(s, s);
            } else {
                matrix.postScale(scaleWidth, scaleHeight);
            }
            //获取新的bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        }
    }
}
