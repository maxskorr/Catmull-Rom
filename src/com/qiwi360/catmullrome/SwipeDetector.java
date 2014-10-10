package com.qiwi360.catmullrome;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Max on 10/10/2014.
 */
public class SwipeDetector implements View.OnTouchListener{
    static final int MIN_DISTANCE = 100;

    private float downX, downY, upX, upY;

    public enum SWIPE_TYPE {RIGHT_TO_LEFT,
        LEFT_TO_RIGHT,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
        JUST_TOUCH}

    private View v;

    private onSwipeEvent swipeEventListener;

    public SwipeDetector(final onSwipeEvent swipeEventListener, final View v) {
        try {
            this.swipeEventListener = swipeEventListener;
        }
        catch (ClassCastException e)  {
            e.printStackTrace();
        }

        this.v = v;
    }

    public void onRightToLeftSwipe(){
        swipeEventListener.SwipeEventDetected(v, SWIPE_TYPE.RIGHT_TO_LEFT);
    }

    public void onLeftToRightSwipe(){
        swipeEventListener.SwipeEventDetected(v, SWIPE_TYPE.LEFT_TO_RIGHT);
    }

    public void onTopToBottomSwipe(){
        swipeEventListener.SwipeEventDetected(v, SWIPE_TYPE.TOP_TO_BOTTOM);
    }

    public void onBottomToTopSwipe(){
        swipeEventListener.SwipeEventDetected(v, SWIPE_TYPE.BOTTOM_TO_TOP);
    }

    public void onJustTouch() {
        swipeEventListener.SwipeEventDetected(v, SWIPE_TYPE.JUST_TOUCH);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //HORIZONTAL SCROLL
                if(Math.abs(deltaX) > Math.abs(deltaY))
                {
                    if(Math.abs(deltaX) > MIN_DISTANCE){
                        // left or right
                        if(deltaX < 0)
                        {
                            this.onLeftToRightSwipe();
                            return true;
                        }
                        if(deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    }
                    else {
                        //not long enough swipe...
                        this.onJustTouch();
                        return true;
                    }
                }
                //VERTICAL SCROLL
                else
                {
                    if(Math.abs(deltaY) > MIN_DISTANCE){
                        // top or down
                        if(deltaY < 0)
                        { this.onTopToBottomSwipe();
                            return true;
                        }
                        if(deltaY > 0)
                        { this.onBottomToTopSwipe();
                            return true;
                        }
                    }
                    else {
                        //not long enough swipe...
                        this.onJustTouch();
                        return true;
                    }
                }

                return true;
            }
        }

        return false;
    }

    public interface onSwipeEvent  {
        public void SwipeEventDetected(View v , SWIPE_TYPE SwipeType);
    }

}