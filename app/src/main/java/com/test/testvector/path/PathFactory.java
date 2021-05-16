package com.test.testvector.path;

import android.graphics.PointF;

/**
 * function:路径工厂
 *
 * @author fengrenjie
 * modify date: 2021/5/14
 */
public class PathFactory {

    private static final String M = "M";
    private static final String C = "C";
    private static final String A = "A";
    private static final String Q = "Q";
    private static final String Z = "Z";
    public static final String L = "L";

    public static final String T = "T";
    public static final String S = "S";

    public static final String H = "H";
    public static final String V = "V";

    /**
     * 解析路径
     *
     * @param path 路径值
     * @return 路劲规则集合
     */
    public static PathRule parse(String path) {
        if (path == null || path.trim().length() == 0) {
            return null;
        }
        String[] datas = path.split(" ");
        PathRule firstRule = null;
        PathRule lastPathRule = null;
        for (String data : datas) {
            if (data == null || data.length() == 0) {
                continue;
            }
            String first = String.valueOf(data.charAt(0));
            PathRule pathRule = null;
            if (M.equalsIgnoreCase(first)) {
                pathRule = new PathMoveTo(first);
            } else if (L.equalsIgnoreCase(first) || H.equalsIgnoreCase(first) || V.equalsIgnoreCase(first)) {
                pathRule = new PathLineTo(first);
            } else if (C.equalsIgnoreCase(first) || S.equalsIgnoreCase(first)) {
                pathRule = new PathCubicTo(first);
            } else if (A.equalsIgnoreCase(first)) {
                pathRule = new PathArcTo(first);
            } else if (Q.equalsIgnoreCase(first) || T.equalsIgnoreCase(first)) {
                pathRule = new PathQuadTo(first);
            } else if (Z.equalsIgnoreCase(first)) {
                pathRule = new PathZ(first);
            }

            if (pathRule != null) {
                pathRule.parseShortPath(data);
                if (lastPathRule != null) {
                    lastPathRule.next = pathRule;
                    pathRule.previous = lastPathRule;
                }
                lastPathRule = pathRule;
            }
            if (firstRule == null) {
                firstRule = pathRule;
            }
        }
        if (firstRule != null) {
            for (PathRule rule = firstRule; rule != null; rule = rule.next) {
                rule.executePath();
            }
        }
        return firstRule;
    }

    /**
     * 计算点x1,y1相对点x2,y2的对称点
     *
     * @param x1 第一个点的X轴坐标
     * @param y1 第一个点的Y轴坐标
     * @param x2 第二个点的X轴坐标
     * @param y2 第二个点的Y轴坐标
     * @return 点x1, y1相对点x2, y2的对称点
     */
    public static PointF calSymmetryPointF(float x1, float y1, float x2, float y2) {
        return new PointF(2 * x2 - x1, 2 * y2 - y1);
    }
}
