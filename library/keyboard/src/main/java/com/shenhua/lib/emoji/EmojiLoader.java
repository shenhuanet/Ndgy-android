package com.shenhua.lib.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenhua.lib.emoji.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 表情加载器
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiLoader {

    private static final int CACHE_MAX_SIZE = 1024;
    private static LruCache<String, Bitmap> mEmojiDrawable;
    private static final String DEFAULT_EMOJI_WEICO = "emoji_weico";
    private static EmojiLoader sInstance;

    public static EmojiLoader getInstance() {
        if (sInstance == null) {
            synchronized (EmojiLoader.class) {
                if (sInstance == null) {
                    sInstance = new EmojiLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * 读取自定义表情组
     *
     * @return list EmojiGroup
     */
    public List<EmojiGroup> getEmojiGroups() {
        List<EmojiGroup> groups = new ArrayList<>();
//        EmojiGroup group = new EmojiGroup(1, R.drawable.);
        return null;
    }

    static {
        mEmojiDrawable = new LruCache<String, Bitmap>(CACHE_MAX_SIZE) {
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != newValue) {
                    oldValue.recycle();
                }
            }
        };
    }

    /**
     * 读取自定义表情
     *
     * @param context
     * @param emojiPath
     * @return
     */
    public static List<Emoji> loadEmoji(Context context, String emojiPath) {
        return new ArrayList<>();
    }

    /**
     * 读取Assets表情列表
     *
     * @param context context
     * @return emojis
     */
    public static List<Emoji> loadAssetEmojis(Context context, String jsonName) {
        String json = StringUtils.getJsonDataFromAssets(context, jsonName);
        Gson gson = new Gson();
        return gson.<ArrayList<Emoji>>fromJson(json, new TypeToken<List<Emoji>>() {
        }.getType());
    }

    static Drawable getDefaultEmojiBitmap(Context context, String fileName) {
        Bitmap cache = mEmojiDrawable.get(DEFAULT_EMOJI_WEICO + File.separator + fileName);
        if (cache == null) {
            cache = loadAssetBitmap(context, DEFAULT_EMOJI_WEICO, fileName);
        }
        return new BitmapDrawable(context.getResources(), cache);
    }

    private static Bitmap loadAssetBitmap(Context context, String dir, String fileName) {
        InputStream is = null;
        try {
            Resources resources = context.getResources();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = DisplayMetrics.DENSITY_HIGH;
            options.inScreenDensity = resources.getDisplayMetrics().densityDpi;
            options.inTargetDensity = resources.getDisplayMetrics().densityDpi;
            is = context.getAssets().open(dir + File.separator + fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            if (bitmap != null) {
                mEmojiDrawable.put(dir + File.separator + fileName, bitmap);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static class Emoji {

        /**
         * id : 001
         * tag : [hh]
         * file : emoji_weico/emoji_001.png
         */

        private String id;
        private String tag;
        private String file;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}
