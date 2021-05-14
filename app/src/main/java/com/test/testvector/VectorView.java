package com.test.testvector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.test.testvector.path.PathFactory;
import com.test.testvector.path.PathRule;

/**
 * function:矢量图形控件（根据路径）
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class VectorView extends View {

    private final Path path = new Path();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    /**
     * 图形缩放比例（相对px值）
     */
    private float scaleDensity;
    /**
     * Vector的宽度
     */
    private float viewportWidth;
    /**
     * Vector的高度
     */
    private float viewportHeight;
    /**
     * 填充颜色
     */
    private int fillColor;
    /**
     * Vector路径
     */
    private String pathData;

    private final PointF translatePoint = new PointF();

    public VectorView(Context context) {
        this(context, null);
    }

    public VectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public VectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 解析路径
     *
     * @param pathData 路径值
     */
    private void parsePath(String pathData) {
        PathRule rules = PathFactory.parse(pathData);
        path.reset();

        for (PathRule rule = rules; rule.hasNext(); rule = rule.next()) {
            rule.executePath(path, scaleDensity);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        float tempWidth, tempHeight;

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            tempWidth = getResources().getConfiguration().densityDpi * viewportWidth;
            tempWidth = Math.min(tempWidth, width);
        } else {
            tempWidth = width;
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            tempHeight = getResources().getConfiguration().densityDpi * viewportHeight;
            tempHeight = Math.min(tempHeight, height);
        } else {
            tempHeight = height;
        }

        scaleDensity = Math.min(tempHeight / viewportHeight, tempWidth / viewportWidth);
        float finalWidth, finalHeight;
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            finalWidth = width;
            finalHeight = height;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            finalWidth = width;
            finalHeight = (viewportHeight * scaleDensity);
        } else if (heightMode == MeasureSpec.EXACTLY) {
            finalWidth = viewportWidth * scaleDensity;
            finalHeight = height;
        } else {
            finalWidth = viewportWidth * scaleDensity;
            finalHeight = (viewportHeight * scaleDensity);
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec((int) finalWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec((int) finalHeight, MeasureSpec.EXACTLY));
        translatePoint.set((getMeasuredWidth() - viewportWidth * scaleDensity) / 2, (getMeasuredHeight() - viewportHeight * scaleDensity) / 2);
        parsePath(pathData);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(translatePoint.x, translatePoint.y);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    /**
     * 设置路径值
     *
     * @param path 路径
     */
    public void setPathData(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }
        this.pathData = path;
        parsePath(pathData);
        invalidate();
    }

    /**
     * 设置填充颜色
     *
     * @param color 颜色值
     */
    public void setFillColor(int color) {
        this.fillColor = color;
        paint.setColor(fillColor);
        invalidate();
    }

}
