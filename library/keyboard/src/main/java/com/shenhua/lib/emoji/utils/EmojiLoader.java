package com.shenhua.lib.emoji.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenhua.lib.emoji.bean.EmojiGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情加载器
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiLoader {

    private static final int CACHE_MAX_SIZE = 1024;
    private static final float SMALL_SCALE = 0.5f;
    private static final Map<String, EmojiGroup.EmojiBean> mEmojiEntry = new HashMap<>();
    private static LruCache<String, Bitmap> sEmojiDrawable;
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

    static {
        sEmojiDrawable = new LruCache<String, Bitmap>(CACHE_MAX_SIZE) {
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != newValue) {
                    oldValue.recycle();
                }
            }
        };
    }

    public EmojiGroup getEmojiGroup(Context context, String dir) {
        String json = StringUtils.getJsonDataFromAssets(context, dir + "/" + dir + ".json");
        Gson gson = new Gson();
        EmojiGroup group = gson.fromJson(json, new TypeToken<EmojiGroup>() {
        }.getType());
        List<EmojiGroup.EmojiBean> emoji = group.getEmoji();
        for (int i = 0; i < emoji.size(); i++) {
            mEmojiEntry.put(emoji.get(i).getTag(), emoji.get(i));
        }
        return group;
    }

    public void setDefaultEmojiGroupIcon(Context context, TabLayout.Tab tab, String name) {
        tab.setIcon(new BitmapDrawable(context.getResources(), loadAssetBitmap(context, name)));
    }

    public void replaceEmoji(Context context, EditText editText, String emojiTag) {
        if (editText == null || TextUtils.isEmpty(emojiTag)) {
            return;
        }
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        editable.replace(start, end, emojiTag);
        end = end + emojiTag.length();
        Drawable drawable = getEmojiDispaly(context, emojiTag);
        if (drawable != null) {
            int width = (int) (drawable.getIntrinsicWidth() * SMALL_SCALE);
            int height = (int) (drawable.getIntrinsicHeight() * SMALL_SCALE);
            drawable.setBounds(0, 0, width, height);
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            editable.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public static void replaceEmoji(Context context, TextView textView) {
        String text = textView.getText().toString();
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        SpannableString spannableString = new SpannableString(text);
        Matcher matcher = Pattern.compile("\\[[^\\[]{1,10}\\]").matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String tag = text.substring(start, end);
            Drawable drawable = getEmojiDispaly(context, tag);
            if (drawable != null) {
                int width = (int) (drawable.getIntrinsicWidth() * SMALL_SCALE);
                int height = (int) (drawable.getIntrinsicHeight() * SMALL_SCALE);
                drawable.setBounds(0, 0, width, height);
                ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                spannableString.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableString);
    }

    public static Drawable getEmojiDispaly(Context context, String text) {
        EmojiGroup.EmojiBean emoji = mEmojiEntry.get(text);
        if (emoji == null || TextUtils.isEmpty(emoji.getTag())) {
            return null;
        }
        Bitmap cache = sEmojiDrawable.get(emoji.getFile());
        if (cache == null) {
            cache = loadAssetBitmap(context, emoji.getFile());
        }
        return new BitmapDrawable(context.getResources(), cache);
    }

    private static Bitmap loadAssetBitmap(Context context, String fileName) {
        InputStream is = null;
        try {
            Resources resources = context.getResources();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = DisplayMetrics.DENSITY_HIGH;
            options.inScreenDensity = resources.getDisplayMetrics().densityDpi;
            options.inTargetDensity = resources.getDisplayMetrics().densityDpi;
            is = context.getAssets().open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            if (bitmap != null) {
                sEmojiDrawable.put(fileName, bitmap);
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

}
