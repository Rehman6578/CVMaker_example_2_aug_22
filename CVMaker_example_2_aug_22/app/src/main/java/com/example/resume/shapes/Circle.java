package com.example.resume.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Project Name : CVMaker
 * Created by   : UMMER SIDDIQUE
 * Created on   : August 23, 2017
 * Created at   : 11:34 AM
 */

public class Circle extends RelativeLayout {

    private Paint paint;
    private Path clipPath;

    public Circle(Context context) {
        super(context);
        paint = new Paint();
        clipPath = new Path();
    }

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        clipPath = new Path();
    }

    public Circle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        clipPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

        clipPath.addPath(circle());
        canvas.clipPath(clipPath);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawPath(clipPath, paint);
        super.onDraw(canvas);
    }

    private Path circle() {
        Path path = new Path();
        path.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2-20, Path.Direction.CCW);
        return path;
    }
}
