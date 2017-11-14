package com.example.typeevaluatortest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @version $Rev$
 * @anthor Administrator
 * @dsc ${TOOD}
 * @updateAuthor $Author
 * @updateDsc ${TOOD}
 */
public class MyAnimView extends View {
    private final static float RADIUS = 50f;

    private String color;

    private Point currentPoint;

    private Paint mPaint;

    public MyAnimView(Context context,AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x,y,RADIUS,mPaint);
    }

    public void startAnimation() {
        //Point startPoint = new Point(getWidth() / 2,RADIUS);
        //Point endPoint = new Point(getWidth() / 2,getHeight() - RADIUS);
        Point startPoint = new Point(getWidth() / 2, RADIUS);
        Point endPoint = new Point(getWidth() / 2, getHeight());
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint = (Point) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        /*ObjectAnimator anim2 = ObjectAnimator.ofObject(this,"color",new ColorEvaluator(),"#0000FF", "#FF0000");
        AnimatorSet set = new AnimatorSet();
        set.play(anim).with(anim2);
        set.setDuration(5000);
        set.start();*/
        anim.setInterpolator(new DecelerateAccelerateInterpolator());
        anim.setDuration(2500);
        anim.start();
    }
}
