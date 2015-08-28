package com.jcgibson.graphwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 8/20/2015.
 */
public class PieChart extends View
{
    private int mInnerCircleColor;
    private int mCircleSize;

    private Paint mArcCirclePaint;
    private Paint mShadow;

    private RectF mBounds;

    private int ww;
    private int hh;

    private List<PieSlice> mPieSlices;

    public PieChart(Context context)
    {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //Retrieve styleable attributes.
        TypedArray a =  context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0);
        try
        {
            mInnerCircleColor = a.getInteger(R.styleable.PieChart_innerCircleColor, 0);
            mCircleSize = a.getInteger(R.styleable.PieChart_circleSize, 4);
        }
        finally
        {
            a.recycle();
        }

        //Initialize slice array.
        mPieSlices = new ArrayList<>();

        //Initialize paint objects.
        init();
    }

    /***
     * Method to initialize paint objects used in onDraw().
     *
     * ***/
    private void init()
    {
        mArcCirclePaint = new Paint();
        mArcCirclePaint.setColor(Color.parseColor("#4423DD"));
        mArcCirclePaint.setAntiAlias(true);
        mArcCirclePaint.setStyle(Paint.Style.FILL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            mArcCirclePaint.setShadowLayer(7.0f, -5.0f, 0.0f, Color.BLACK);
            setLayerType(LAYER_TYPE_SOFTWARE, mArcCirclePaint);
        }
    }

    /***
     * This method is called when View is resized and needs to be redrawn.
     * Use to determine dimensions and positions as needed.
     *
     * @param w -> New width of the View.
     * @param h -> New height of the View.
     * @param oldw -> Previous width of the View, 0 if it is the first call.
     * @param oldh -> Previous height of the View, 0 if it is the first call.
     * ***/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        int leftPos;
        int rightPos;
        int bottomPos;
        int topPos;

        //Screen is in portrait mode.
        /*
        if(h >= w)
        {
            leftPos = w / mCircleSize;
            rightPos = (3 * w) / mCircleSize;
            topPos = w / mCircleSize;
            bottomPos = (3 * w) / mCircleSize;
        }
        //Screen is in landscape mode.
        else
        {
            leftPos = h / mCircleSize;
            rightPos = (3 * h) / mCircleSize;
            topPos = h / mCircleSize;
            bottomPos = (3 * h) / mCircleSize;
        }*/

        leftPos = 0 + 15;
        rightPos = w - 40;
        bottomPos = h - 40;
        topPos = 0 + 15;

        Log.d("onSizeChanged", "w is " + w + ", and h is " + h);
        Log.d("onSizeChanged", "onSizeChange called with leftPos = " + leftPos + ", rightPos = " + rightPos + ", topPos " + topPos + ", and bottomPos " + bottomPos);

        mBounds = new RectF(leftPos, topPos, rightPos, bottomPos);
    }

    /***
     * Tell the OS how big I want the view to be dependant on the layout constraints of the parent View.
     *
     * Defining height or width in here with setMeasuredDimension(), affects the value that is passed into onSizeChanged as h or w.
     * ***/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int finalWidth;
        int finalHeight;
/*
        if (widthMode == MeasureSpec.EXACTLY)
        {
            //Must be this size
            finalWidth = widthSize;
        }
        else if(widthMode == MeasureSpec.AT_MOST)
        {
            //Can't be bigger than...
            finalWidth = widthSize;
        }
        else
        {
            //Be whatever you want
            finalWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            //Must be this size
            finalHeight = heightSize / 2;
        } else if (heightMode == MeasureSpec.AT_MOST)
        {
            //Can't be bigger than...
            finalHeight = heightSize / 2;
        } else
        {
            //Be whatever you want
            finalHeight = heightSize / 2;
        }
*/
        finalWidth = heightSize / 3;
        finalHeight = heightSize / 3;

        Log.d("onMeasure", "available width = " + widthSize + ", and height = " + heightSize);
        Log.d("onMeasure", "setMeasuredDimension of width = " + finalWidth + ", and height = " + finalHeight);

        setMeasuredDimension(finalWidth, finalHeight);
    }

    /***
     * Method to draw to the screen.
     *
     * @param canvas -> Canvas on which the background will be drawn.
     * ***/
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        calculateRelativeSizes();

        float startAngle = 0.0f;
        for(PieSlice s : mPieSlices)
        {
            mArcCirclePaint.setColor(s.getSliceColor());
            canvas.drawArc(mBounds, startAngle, s.getRelativeSize(), true, mArcCirclePaint);
            startAngle = startAngle + s.getRelativeSize();
        }
    }

    /***
     * Method to add a 'slice' to the pie.
     *
     * @param color -> Specifies the color to use for the slice.
     * @param size -> Specifies the size of the slice.  This value will be compared and used proportionally to all other slices.
     * ***/
    public void addSlice(int color, float size, String label)
    {
        PieSlice slice = new PieSlice(color, size, label);
        mPieSlices.add(slice);
        invalidate();
    }

    /***
     * Method to determine the proportional size of each 'slice' in the total 'pie'.
     *
     * ***/
    private void calculateRelativeSizes()
    {
        float totalSize = 0;

        for(PieSlice s: mPieSlices)
        {
            totalSize = totalSize + s.getSliceSize();
        }
        for(PieSlice s: mPieSlices)
        {
            s.setRelativeSize((360.0f * s.getSliceSize()) / totalSize);
        }
    }
}
