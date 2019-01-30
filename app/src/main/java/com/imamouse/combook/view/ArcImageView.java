package com.imamouse.combook.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.imamouse.combook.R;

public class ArcImageView extends android.support.v7.widget.AppCompatImageView {
    /*
     * 弧形高度
     */
    private int mArcHeight;
    private static final String TAG = "ArcImageView";

    public ArcImageView(Context context) {
        this(context, null);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcHeight, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, getHeight() - 2 * mArcHeight);
        path.quadTo(getWidth() / 2, getHeight(), getWidth(), getHeight() - 2 * mArcHeight);
        path.lineTo(getWidth(), 0);
        path.close();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}

/*
 *  作者：殇神马 - CSDN
 *  原文：https://blog.csdn.net/mq2856992713/article/details/78635790
 ****/
