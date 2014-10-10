package com.qiwi360.catmullrome.spline.model;

import android.graphics.PointF;

/**
 * Created by Max on 10/10/2014.
 */
public class CRSegment {
    private final PointF[] points;

    public CRSegment() {
        points = new PointF[4];

        for (int i = 0; i < 3; i++) {
            points[i] = new PointF();
        }
    }

    private float calcVar(final float v0,
                          final float v1,
                          final float v2,
                          final float v3,
                          final float t) {

        final float t2 = t * t;
        final float t3 = t2 * t;

        return 0.5f * ((2.0f * v1) +
                (-v0 + v2) * t +
                (2.0f * v0 - 5.0f * v1 + 4f * v2 - v3) * t2 +
                (-v0 + 3.0f * v1 - 3.0f * v2 + v3) * t3);
    }

    public PointF calc(float t) {
        return new PointF(calcVar(points[0].x, points[1].x, points[2].x, points[3].x, t),
                calcVar(points[0].y, points[1].y, points[2].y, points[3].y, t));
    }

    public void setPoint(final int index, final PointF point) throws Exception {
        if (index > 3 || index < 0)
            throw new Exception("Wrong index. Should be from 0 to 3");

        points[index] = point;
    }

    public PointF getPoint(final int index) throws Exception {
        if (index > 3 || index < 0)
            throw new Exception("Wrong index. Should be from 0 to 3");

        return points[index];
    }
}
