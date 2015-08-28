package com.jcgibson.graphwidgets;


/**
 * Created by Jordan on 8/20/2015.
 *
 * This is the data structure representing a segment or 'slice' of the pie graph.
 *
 */
public class PieSlice
{
    private int mSliceColor;
    private float mSliceSize;
    private float mRelativeSize;
    private String mLabel;

    protected PieSlice(int sliceColor, float sliceSize, String label)
    {
        mSliceColor = sliceColor;
        mSliceSize = sliceSize;
        mRelativeSize = 0;
        mLabel = label;
    }

    protected int getSliceColor()
    {
        return mSliceColor;
    }

    protected float getSliceSize()
    {
        return mSliceSize;
    }

    protected float getRelativeSize()
    {
        return mRelativeSize;
    }

    protected void setRelativeSize(float relativeSize)
    {
        mRelativeSize = relativeSize;
    }

    protected void setLabel(String label)
    {
        mLabel = label;
    }

    protected String getLabel()
    {
        return mLabel;
    }
}
