package tech.com.commoncore.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observer;

/**
 * Time:2019/1/15
 * Desc:
 */
public class VideoUtil {
    public static final String[] videos = {
            "《馅饼 陷阱》 广发金融知识普及短片",
            "互联网金融、金融创新授课金融学家宏皓讲投资银行操作实务（六）",
            "加拿大金融专业就业前景怎么样- 工作好找吗",
            "金融_理财_房地产的骗局_都是用金融杠杠来骗老百姓的钱",
            "金融交易心理分析：为什么一个正常人，进入股市就疯狂",
            "金融消费者的八项基本权利，本溪市金融办和本溪市银监分局联合发布金融公益广告",
            "金融小百科-完结篇-香港低价股",
            "金融小百科-香港创业板股风险须知",
            "金融小百科-香港新股与次新股要慎炒",
            "金融小百科-CDR简介",
            "金融学院-超短线趋势投资策略的操作重点",
            "金融学院-解读巴菲特的价值投资",
            "金融学院-盘中涨停板打开的几种操作建议",
            "金融学院-小崔引发的血馒头，华谊兄弟能否咽下",
            "看女性如何助解决冰岛金融危机——凌石金融",
            "马云/ 未来的金融要靠什么来发展",
            "马云分析未来金融行业趋势，不愧是爸爸！",
            "骗子最恨的视频！看完这个短片让你轻松原理金融诈骗",
            "识别违法金融广告 远离非法金融活动",
            "亚洲金融论坛：「区块链技术」创造新商业价值",
            "如何在金融市场中盈利",
            "绿色金融",
            "俞凌雄互联网金融的未来发展趋势"
    };

    private static String host= "http://stock.fk7h.com/video/";
    //    http://stock.fk7h.com/video/%E7%BB%8F%E6%B5%8E%E9%87%91%E8%9E%8D%E4%BC%9A%E8%AE%A1.mp4
    public static String getRealUrl(String name){
        return  host + name+ ".mp4";
    }

    /**
     * 获取视频第一帧作为缩略图
     */
    private static Bitmap getVideoThumb(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(videoUrl, new HashMap());
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 获取视频第一帧作为缩略图
     */
    public static void getVideoThumb(final String videoUrl, final OnResultListener listener) {

                Observable.create(new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                        Bitmap bitmap = getVideoThumb(videoUrl);
                        emitter.onNext(bitmap);
                        emitter.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (listener != null)
                            listener.onResult(bitmap);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public interface OnResultListener {
        void onResult(Bitmap bitmap);
    }

}
