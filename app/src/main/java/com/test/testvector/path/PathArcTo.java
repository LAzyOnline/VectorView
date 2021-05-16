package com.test.testvector.path;

import android.graphics.Path;
import android.graphics.PointF;

import androidx.annotation.Nullable;

/**
 * function:使用圆弧连接Path。执行Path.arcTo操作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathArcTo extends PathRule {

    private static final int SIZE = 7;
    private static final int INDEX_RX = 0;
    private static final int INDEX_RY = 1;
    private static final int INDEX_X_ROTATION = 2;
    private static final int INDEX_LARGE_ARC = 3;
    private static final int INDEX_SWEEP = 4;
    private static final int INDEX_FINISH_X = 5;
    private static final int INDEX_FINISH_Y = 6;

    PathArcTo(String key) {
        super(key);
    }

    @Override
    public void attachToPath(@Nullable Path path, float density) {
        if (path == null) {
            return;
        }
        float rx = points.get(INDEX_RX);
        float ry = points.get(INDEX_RY);
        PointF startPoint, endPoint;
        if (previous == null) {
            startPoint = new PointF(0, 0);
        } else {
            startPoint = previous.getLastAbsPointF();
        }
        endPoint = new PointF(absPoints.get(INDEX_FINISH_X), absPoints.get(INDEX_FINISH_Y));
        ArcValue value = convert(startPoint.x, startPoint.y, endPoint.x, endPoint.y, points.get(INDEX_LARGE_ARC).intValue(), points.get(INDEX_SWEEP).intValue(), rx, ry, points.get(INDEX_X_ROTATION));
        if (value == null) {
            return;
        }
        path.arcTo((float) (value.cx - rx), (float) (value.cy - ry), (float) (value.cx + rx), (float) (value.cy + ry), (float) value.theta1, (float) value.delta_theta, false);
    }

    @Override
    protected int pointSize() {
        return SIZE;
    }

    @Override
    protected void calAbsPoints() {
        absPoints.clear();
        //如果不是小数或者当前是第一条规则，那么认定为绝对坐标
        if (!isLowCase() || previous == null) {
            absPoints.addAll(points);
            return;
        }
        //获取上一次路径的结束点
        PointF previousPos = previous.getLastAbsPointF();

        for (int i = 0; i < points.size(); i++) {
            if (i < INDEX_FINISH_X) {
                absPoints.add(points.get(i));
            } else {
                absPoints.add(i % 2 != 0 ? (previousPos.x + points.get(i)) : (previousPos.y + points.get(i)));
            }
        }
    }

    /**
     * 计算弧度
     *
     * @param ux 向量 u的x
     * @param uy 向量 u的y
     * @param vx 向量v的x
     * @param vy 向量v的y
     * @return 弧度
     */
    private double calRadian(double ux, double uy, double vx, double vy) {
        double dot = ux * vx + uy * vy;
        double mod = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy));
        double rad = Math.acos(dot / mod);
        if (ux * vy - uy * vx < 0.0) {
            rad = -rad;
        }
        return rad;
    }

    /**
     * 从端点到中心参数化的转换<br/>
     * <a href="https://blog.csdn.net/iteye_3606/article/details/82475809">参考</a><br/>
     * <a href="https://www.w3.org/TR/2011/REC-SVG11-20110816/implnote.html#ArcImplementationNotes">原始资料</a>
     *
     * @param x1  起点X坐标
     * @param y1  起点Y坐标
     * @param x2  终点X
     * @param y2  终点Y
     * @param fA  大弧标志（如果选择跨度小于或等于180的弧，值为0；如果选择跨度大于180的弧，值为1）
     * @param fS  圆弧方向（顺时针为1，逆时针为0）
     * @param rx  半长轴
     * @param ry  半短轴
     * @param phi 从当前坐标系的X轴到椭圆的X轴的角度
     * @return 圆弧描述值
     */
    @Nullable
    private ArcValue convert(float x1, float y1, float x2, float y2, int fA, int fS, float rx, float ry, float phi) {
        double cx, cy, theta1, delta_theta;
        if (rx == 0.0 || ry == 0.0) {
            return null;
        }
        double s_phi = Math.sin(phi);
        double c_phi = Math.cos(phi);
        double hd_x = (x1 - x2) / 2.0;
        double hd_y = (y1 - y2) / 2.0;
        double hs_x = (x1 + x2) / 2.0;
        double hs_y = (y1 + y2) / 2.0;

        // F6.5.1
        double x1_ = c_phi * hd_x + s_phi * hd_y;
        double y1_ = c_phi * hd_y - s_phi * hd_x;

        double rxry = rx * ry;
        double rxy1_ = rx * y1_;
        double ryx1_ = ry * x1_;
        double sum_of_sq = rxy1_ * rxy1_ + ryx1_ * ryx1_;   // sum of square
        double coe = Math.sqrt((rxry * rxry - sum_of_sq) / sum_of_sq);
        if (fA == fS) {
            coe = -coe;
        }

        // F6.5.2
        double cx_ = coe * rxy1_ / ry;
        double cy_ = -coe * ryx1_ / rx;

        // F6.5.3
        cx = c_phi * cx_ - s_phi * cy_ + hs_x;
        cy = s_phi * cx_ + c_phi * cy_ + hs_y;

        double xcr1 = (x1_ - cx_) / rx;
        double xcr2 = (x1_ + cx_) / rx;
        double ycr1 = (y1_ - cy_) / ry;
        double ycr2 = (y1_ + cy_) / ry;

        // F6.5.5
        theta1 = calRadian(1.0, 0.0, xcr1, ycr1);

        // F6.5.6
        delta_theta = calRadian(xcr1, ycr1, -xcr2, -ycr2);
        double PIx2 = Math.PI * 2.0;
        while (delta_theta > PIx2) {
            delta_theta -= PIx2;
        }
        while (delta_theta < 0.0) {
            delta_theta += PIx2;
        }
        if (fS == 0) {
            delta_theta -= PIx2;
        }

        return new ArcValue(cx, cy, theta1, delta_theta);
    }

    /**
     * 圆弧描述
     */
    private static class ArcValue {
        /**
         * 原点
         */
        private double cx, cy;
        /**
         * 起始角度
         */
        private double theta1;
        /**
         * 画过的角度（起始角度和终止角度之差）
         */
        private double delta_theta;

        ArcValue(double cx, double cy, double theta1, double delta_theta) {
            this.cx = cx;
            this.cy = cy;
            this.theta1 = theta1;
            this.delta_theta = delta_theta;
        }
    }
}
