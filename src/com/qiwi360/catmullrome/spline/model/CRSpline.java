package com.qiwi360.catmullrome.spline.model;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import com.qiwi360.catmullrome.MainActivity;
import com.qiwi360.catmullrome.spline.view.SplineDrawer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Max on 10/9/2014.
 */
public class CRSpline {
    public final List<PointF> points;
    public final List<CRSegment> segments;
    private SplineDrawer drawer;

    public CRSpline() throws Exception {
        this.points = new LinkedList<>();
        this.segments = new LinkedList<>();

        fillRandomly(6);
    }

    public void draw(final Canvas canvas) throws Exception {
        if (drawer == null)
            return;

        drawer.onDraw(canvas);
    }

    public void fillRandomly(int nodesAmount) throws Exception {
        points.clear();

        int w = 600; // DANGER! HARDCODE!!!
        int h = 800;

        if (MainActivity.instance != null) {
            DisplayMetrics dimension = new DisplayMetrics();
            MainActivity.instance.getWindowManager().getDefaultDisplay().getMetrics(dimension);
            w = dimension.widthPixels;
            h = dimension.heightPixels;
        }

        final Random random = new Random();

        for (int i = 0, x = random.nextInt(w), y = random.nextInt(h); i < nodesAmount; i++,
                x = random.nextInt(w), y = random.nextInt(h))
            points.add(new PointF(x, y));

        generateSegments();
    }

    private void generateSegments() throws Exception {
        segments.clear();

        for (int i = 0; i < points.size() - 3; i++) {
            final CRSegment segment = new CRSegment();

            for (int j = 0; j < 4; j++) {
                segment.setPoint(j, points.get(i + j));
            }

            segments.add(segment);
        }
    }

    public void setDrawer(final SplineDrawer drawer) {
        this.drawer = drawer;
    }

    public PointF getNearestPoint(float x, float y, int limit) {
        if (limit <= 0)
            limit = 5;

        for (final PointF point: points)
            if (Math.abs(point.x - x) <= limit
                    &&  Math.abs(point.y - y) <= limit)
                return point;

        return null;
    }

    public void addPoint(final PointF point) throws Exception {
        points.add(point);
        generateSegments();
    }
}
