package com.test.testvector.path;

import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * function:三阶贝塞尔曲线Path。执行Path.cubicTo操作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathCubicTo extends PathRule {

    private static final String TAG = "PathCubicTo";

    private static final int C_SIZE = 6;
    private static final int S_SIZE = 4;

    PathCubicTo(String key) {
        super(key);
    }

    @Override
    protected void executePath() {
        completionControl();
        super.executePath();
    }

    /**
     * 补全T的控制点，后面逻辑全部作Q/q处理
     */
    private void completionControl() {
        if (PathFactory.S.equalsIgnoreCase(key)) {
            if (!(previous instanceof PathCubicTo)) {
                Log.e(TAG, "Encountered an error, the instruction before the current instruction S should be the C or S instruction. But the previous instruction of the current instruction is " + (previous != null ? previous.key : "NULL"));
                return;
            }
            //因为T相对Q,T的控制点为上一个贝塞尔曲线的控制点相对终点的中心对称点，也就是说需要知道最后两个点的坐标，此处startPos的计算就是往前取4个值
            int startPos = previous.points.size() - S_SIZE;
            //控制点
            PointF controlPointF = PathFactory.calSymmetryPointF(previous.absPoints.get(startPos), previous.absPoints.get(startPos + 1), previous.absPoints.get(startPos + 2), previous.absPoints.get(startPos + 3));
            /* 转换为相对坐标 */
            if (isLowCase()) {
                PointF lastPointF = previous.getLastAbsPointF();
                controlPointF.set(controlPointF.x - lastPointF.x, controlPointF.y - lastPointF.y);
            }
            //将坐标点新增至points集合的前两项
            points.add(0, controlPointF.y);
            points.add(0, controlPointF.x);
        }
    }


    @Override
    public void attachToPath(@Nullable Path path, float density) {
        if (path == null) {
            return;
        }
        path.cubicTo(absPoints.get(0) * density, absPoints.get(1) * density, absPoints.get(2) * density, absPoints.get(3) * density, absPoints.get(4) * density, absPoints.get(5) * density);
    }

    @Override
    protected int pointSize() {
        return C_SIZE;
    }
}
