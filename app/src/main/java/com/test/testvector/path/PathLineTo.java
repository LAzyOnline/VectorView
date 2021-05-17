package com.test.testvector.path;

import android.graphics.Path;
import android.graphics.PointF;

import androidx.annotation.Nullable;

/**
 * function:使用线段连接某个点。执行Path.lineTo动作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathLineTo extends PathRule {

    private static final int SIZE = 2;
    private static final int H_V_SIZE = 1;

    PathLineTo(String key) {
        super(key);
    }

    @Override
    public void attachToPath(@Nullable Path path, float density) {
        if (path == null) {
            return;
        }
        path.lineTo(absPoints.get(0) * density, absPoints.get(1) * density);
    }

    @Override
    protected int pointSize() {
        if (PathFactory.L.equalsIgnoreCase(key)) {
            return SIZE;
        } else {
            return H_V_SIZE;
        }
    }

    @Override
    protected void calAbsPoints() {
        if (PathFactory.L.equalsIgnoreCase(key)) {
            super.calAbsPoints();
        } else {
            //如果是H或者V指令，只有一个值
            absPoints.clear();
            //如果不是小数或者当前是第一条规则，那么认定为绝对坐标
            if (!isLowCase() || previous == null) {
                absPoints.addAll(points);
                return;
            }
            //获取上一次路径的结束点
            PointF previousPos = previous.getLastAbsPointF();
            boolean isHorizontal = PathFactory.H.equalsIgnoreCase(key);

            if (isHorizontal) {
                absPoints.add(previousPos.x + points.get(0));
                absPoints.add(previousPos.y);
            } else {
                absPoints.add(previousPos.x);
                absPoints.add(previousPos.y + points.get(0));
            }
        }
    }
}
