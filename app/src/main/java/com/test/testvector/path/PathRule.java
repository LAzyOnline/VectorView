package com.test.testvector.path;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * function:路径规则实体，用于描述Path
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public abstract class PathRule implements Iterator<PathRule> {

    private static final String SPLIT = ",";

    protected final String key;

    /**
     * 上一个路径规则
     */
    protected PathRule previous;
    protected PathRule next;

    /**
     * 解析出来的原始点
     */
    protected final List<Float> points = new ArrayList<>();

    /**
     * 计算出来的绝对坐标点
     */
    protected final List<Float> absPoints = new ArrayList<>();

    PathRule(String key) {
        this.key = key;
    }

    /**
     * 执行Path操作
     *
     * @param path    Path对象
     * @param density 缩放比例
     */
    public void executePath(Path path, float density) {
        checkData();
        calAbsPoints();
        attachToPath(path, density);
    }

    /**
     * 将顶点值附加到Path
     *
     * @param path    {@link Path}对象，用于执行具体的动作
     * @param density 缩放比例
     */
    protected abstract void attachToPath(Path path, float density);

    /**
     * 指令对应的值的集合的大小
     *
     * @return 指令对应的值的集合的大小
     */
    protected abstract int pointSize();

    /**
     * 解析短路径指令。
     *
     * @param shortPath 表示一个指令字母从头至尾的数据。例如：M1.0，2.0
     */
    protected void parseShortPath(String shortPath) {
        if (shortPath == null || shortPath.isEmpty()) {
            return;
        }
        if (!shortPath.startsWith(key)) {
            return;
        }
        points.clear();
        String result = shortPath.replace(key, "");
        String[] pointStrs = result.split(SPLIT);
        for (String pointStr : pointStrs) {
            points.add(Float.parseFloat(pointStr));
        }
    }

    /**
     * 检查数据的准确性
     */
    protected void checkData() {
        if (points.size() != pointSize()) {
            throw new RuntimeException(String.format("The current path rule does not match its expected length. The length should be %d, the actual length is %d", pointSize(), points.size()));
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public PathRule next() {
        return next;
    }

    /**
     * 是否是小写字母
     *
     * @return true表示是小写字母
     */
    protected boolean isLowCase() {
        if (key == null || key.isEmpty()) {
            return false;
        }
        char first = key.charAt(0);
        return first >= 'a' && first <= 'z';
    }

    /**
     * 计算绝对坐标点
     */
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
            //单数表示X轴，双数表示Y轴
            absPoints.add(i % 2 == 0 ? (previousPos.x + points.get(i)) : (previousPos.y + points.get(i)));
        }
    }

    /**
     * 获取当前路径规则的结束点
     *
     * @return 当前路径规则的结束点
     */
    protected PointF getLastAbsPointF() {
        return new PointF(absPoints.get(absPoints.size() - 2), absPoints.get(absPoints.size() - 1));
    }

}
