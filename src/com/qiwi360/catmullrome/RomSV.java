package com.qiwi360.catmullrome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.qiwi360.catmullrome.spline.model.CRSpline;
import com.qiwi360.catmullrome.spline.view.SimpleSplineDrawer;
import com.qiwi360.catmullrome.spline.view.SplineDrawer;

/**
 * Created by Max on 10/9/2014.
 */
public class RomSV extends SurfaceView implements SurfaceHolder.Callback {
    private CRSpline crSpline;
    private SplineDrawer splineDrawer;
    private boolean dragging = false;
    private PointF pointDragging = null;

    public void init() throws Exception {
        getHolder().addCallback(this);
        setWillNotDraw(false);
        crSpline = new CRSpline();
        splineDrawer = new SimpleSplineDrawer(crSpline);
        crSpline.setDrawer(splineDrawer);
    }

    public RomSV(Context context) throws Exception {
        super(context);
        init();
    }

    public RomSV(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        init();
    }

    public RomSV(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RomSV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) throws Exception {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void onDraw(Canvas canvas) {
        try {
            crSpline.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointDragging = crSpline.getNearestPoint(e.getX(), e.getY(), 0);
                dragging = pointDragging != null;

                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!dragging)
                    return false;

                pointDragging.set(e.getX(), e.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (dragging == false && pointDragging == null)
                    try {
                        crSpline.addPoint(new PointF(e.getX(), e.getY()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                pointDragging = null;
                dragging = false;
                invalidate();
                return true;
        }

        return false;
    }
}
