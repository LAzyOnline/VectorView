package com.test.testvector.path;

import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

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
    protected void attachToPath(Path path, float density) {
        if (PathFactory.S.equalsIgnoreCase(key)) {
            if (!(previous instanceof PathCubicTo)) {
                Log.e(TAG, "Encountered an error, the instruction before the current instruction S should be the C or S instruction. But the previous instruction of the current instruction is " + (previous != null ? previous.key : "NULL"));
                return;
            }
            //因为S相对C少了一个控制点，而这个少了的控制点是通过最后一个控制点和终点计算出来，也就是说需要知道最后两个点的坐标，此处startPos的计算就是往前取4个值
            int startPos = absPoints.size() - S_SIZE;
            PointF controlPointF = PathFactory.calSymmetryPointF(previous.absPoints.get(startPos), previous.absPoints.get(startPos + 1), previous.absPoints.get(startPos + 2), previous.absPoints.get(startPos + 3));
            path.cubicTo(controlPointF.x * density, controlPointF.y * density, absPoints.get(0) * density, absPoints.get(1) * density, absPoints.get(2) * density, absPoints.get(3) * density);
        } else {
            path.cubicTo(absPoints.get(0) * density, absPoints.get(1) * density, absPoints.get(2) * density, absPoints.get(3) * density, absPoints.get(4) * density, absPoints.get(5) * density);
        }
    }

    @Override
    protected int pointSize() {
        return PathFactory.S.equalsIgnoreCase(key) ? S_SIZE : C_SIZE;
    }
}
