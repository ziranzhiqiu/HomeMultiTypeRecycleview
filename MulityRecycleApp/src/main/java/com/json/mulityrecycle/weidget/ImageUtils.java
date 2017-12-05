package com.json.mulityrecycle.weidget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.json.mulityrecycle.Myappplication;
import com.json.mulityrecycle.R;


public class ImageUtils {
    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图 ！！！
    public static void load(Activity activity, String url, ImageView iv) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                Glide.with(Myappplication.mContext).load(url).error(R.mipmap.ic_launcher).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            }
        }
    }


    public static void load(Context context, String url, ImageView iv) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.ic_launcher)
                    .into(iv);
        }
    }

    //不缓存，全部从网络加载
    public static void loadAll(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.ic_launcher)
                .into(iv);
    }

    //不缓存，全部从网络加载
    public static void loadAll(Activity activity, String url, ImageView iv) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                Glide.with(activity).load(url).error(R.mipmap.ic_launcher).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
            }
        }
    }
}
