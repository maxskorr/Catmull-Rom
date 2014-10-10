package com.qiwi360.catmullrome.spline.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import com.qiwi360.catmullrome.spline.model.CRSegment;
import com.qiwi360.catmullrome.spline.model.CRSpline;

import java.util.Random;

/**
 * Created by Max on 10/10/2014.
 */
public class SimpleSplineDrawer implements SplineDrawer {
    private final Paint splinePaint;
    private final Paint textPaint;
    private final Paint wireframePaint;
    private final CRSpline spline;

    public SimpleSplineDrawer(final CRSpline spline) {
        this.spline = spline;
        splinePaint = new Paint();
        textPaint = new Paint();
        wireframePaint = new Paint();

        splinePaint.setColor(Color.GREEN);
        splinePaint.setStrokeWidth(2.0f);

        textPaint.setColor(Color.YELLOW);
        textPaint.setTextSize(textPaint.getTextSize() * 2f);

        wireframePaint.setColor(Color.RED);
    }

    @Override
    public void onDraw(Canvas canvas) throws Exception {
        final Random random = new Random();

        PointF fromPoint = spline.points.get(0);
        for (int i = 0; i < spline.points.size() - 1; i++) {
            final PointF point = spline.points.get(i + 1);

            canvas.drawLine(fromPoint.x, fromPoint.y,
                    point.x, point.y, wireframePaint);

            fromPoint = point;
        }

        for (final PointF point: spline.points)
            canvas.drawCircle(point.x, point.y, 5f, splinePaint);

        for (int i = 0; i < spline.segments.size(); i++) {
            final CRSegment segment = spline.segments.get(i);
            fromPoint = segment.getPoint(1);

            for (int j = 0; j < 100; j++) {
                float t = j * 0.01f;

                PointF point = segment.calc(t);

                canvas.drawLine(fromPoint.x, fromPoint.y,
                        point.x, point.y, splinePaint);
                fromPoint = point;
            }
        }

        for (int i = 0; i < spline.points.size(); i++) {
            PointF pointFrom;
            PointF pointTo = spline.points.get(i);

            if (i == spline.points.size() - 1)
                pointFrom = spline.points.get(i - 1);
            else
                pointFrom = spline.points.get(i + 1);

            float tan = (pointFrom.y - pointTo.y)/(pointFrom.x - pointTo.x);

            float x = spline.points.get(i).x;
            float y = spline.points.get(i).y;

            if (tan > 0f) {
                int sign = -1 * (spline.getNearestPoint(x, y, 20) == null ? -1 : 1);
                x += sign * 10;
                y += sign * 10;
            } else {
                int sign = -1 * (spline.getNearestPoint(x, y, 20) == null ? -1 : 1);
                x -= sign * 10;
                y += sign * 10;
            }

            canvas.drawText("" + i, x, y, textPaint);
        }
    }
}
