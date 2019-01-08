package com.imamouse.combook.binding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageViewAdapter {

    @BindingAdapter(value = {"bitmapPic"}, requireAll = false)
    public static void setImageUri(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null) {
            //使用Glide框架加载图片
            imageView.setImageBitmap(bitmap);
        }
    }
}
