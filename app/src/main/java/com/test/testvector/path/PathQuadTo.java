package com.test.testvector.path;

import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

/**
 * function:二阶贝塞尔区线Path。执行Path.quadTo()操作
 *
 * @author fengrenjie
 * modify date: 2021/5/14
 */
public class PathQuadTo extends PathRule {

    private static final String TAG = "PathQuadTo";

    private static final int Q_SIZE = 4;
    private static final int T_SIZE = 2;

    PathQuadTo(String key) {
        super(key);
    }

    @Override
    protected void attachToPath(Path path, float density) {

        if (PathFactory.T.equalsIgnoreCase(key)) {
            if (!(previous instanceof PathCubicTo)) {
                Log.e(TAG, "Encountered an error, the instruction before the current instruction T should be the Q or T instruction. But the previous instruction of the current instruction is " + (previous != null ? previous.key : "NULL"));
                return;
            }
            //因为T相对Q,T的控制点为上一个贝塞尔区线的控制点相对终点的中心对称点，也就是说需要知道最后两个点的坐标，此处startPos的计算就是往前取4个值
            int startPos = absPoints.size() - Q_SIZE;
            PointF controlPointF = PathFactory.calSymmetryPointF(previous.absPoints.get(startPos), previous.absPoints.get(startPos + 1), previous.absPoints.get(startPos + 2), previous.absPoints.get(startPos + 3));
            path.quadTo(controlPointF.x * density, controlPointF.y * density, absPoints.get(0) * density, absPoints.get(1) * density);
        } else {
            path.quadTo(absPoints.get(0) * density, absPoints.get(1) * density, absPoints.get(2) * density, absPoints.get(3) * density);
        }

    }

    @Override
    protected int pointSize() {
        return PathFactory.T.equalsIgnoreCase(key) ? T_SIZE : Q_SIZE;
    }
}
