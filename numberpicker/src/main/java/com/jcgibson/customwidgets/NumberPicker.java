package com.jcgibson.customwidgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jordan on 8/14/2015.
 */
public class NumberPicker extends LinearLayout
{
    private TextView mNumberTextView;
    private ImageView mIncrementButton;
    private ImageView mDecrementButton;

    private int mMinValue;
    private int mMaxValue;
    private int mIncValue;
    private int mCurrentValue;

    public NumberPicker(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //Get styleable attributes.
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberPicker, 0, 0);
        String textColor;
        if(a.getString(R.styleable.NumberPicker_textColor) != null)
        {
            textColor = a.getString(R.styleable.NumberPicker_textColor);
        }
        else
        {
            textColor = "#000000";
        }
        String arrowColor;
        if(a.getString(R.styleable.NumberPicker_arrowColor) != null)
        {
            arrowColor = a.getString(R.styleable.NumberPicker_arrowColor);
        }
        else
        {
            arrowColor = "#000000";
        }
        String buttonBackgroundColor;
        if(a.getString(R.styleable.NumberPicker_buttonBackgroundColor) != null)
        {
            buttonBackgroundColor = a.getString(R.styleable.NumberPicker_buttonBackgroundColor);
        }
        else
        {
            buttonBackgroundColor = "#FFFFFF";
        }
        mMinValue = a.getInt(R.styleable.NumberPicker_minValue, 0);
        mMaxValue = a.getInt(R.styleable.NumberPicker_maxValue, 9);
        mIncValue = a.getInt(R.styleable.NumberPicker_incValue, 1);
        mCurrentValue = mMinValue;
        a.recycle();

        //Inflate the layout.
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker_layout, this, true);

        //Get references to view elements.
        mNumberTextView = (TextView)findViewById(R.id.numberTextView);
        mIncrementButton = (ImageView)findViewById(R.id.incButton);
        mDecrementButton = (ImageView)findViewById(R.id.decButton);

        //Set styleable attributes.
        setTextColor(textColor);
        setArrowColor(arrowColor);
        setButtonBackgroundColor(buttonBackgroundColor);
        mNumberTextView.setText(mCurrentValue + "");

        //Handle Increment Button Click
        mIncrementButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                incrementValue();
            }
        });

        //Handle Decrement Button Click
        mDecrementButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                decrementValue();
            }
        });
    }

    public NumberPicker(Context context)
    {
        this(context, null);
    }

    /*
    * **Attribute Methods**
    * */

    public void setTextColor(String color)
    {
        mNumberTextView.setTextColor(Color.parseColor(color));
    }

    public void setArrowColor(String color)
    {
        mIncrementButton.setImageTintList(ColorStateList.valueOf(Color.parseColor(color)));
        mDecrementButton.setImageTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }

    public void setButtonBackgroundColor(String color)
    {
        mIncrementButton.setBackgroundColor(Color.parseColor(color));
        mDecrementButton.setBackgroundColor(Color.parseColor(color));
    }

    public int getMinValue()
    {
        return mMinValue;
    }

    public void setMinValue(int minValue)
    {
        mMinValue = minValue;
    }

    public int getMaxValue()
    {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue)
    {
        mMaxValue = maxValue;
    }

    public int getIncValue()
    {
        return mIncValue;
    }

    public void setIncValue(int incValue)
    {
        mIncValue = incValue;
    }

    public int getCurrentValue()
    {
        return mCurrentValue;
    }

    public void setCurrentValue(int currentValue)
    {
        mCurrentValue = currentValue;
        mNumberTextView.setText(Integer.toString(mCurrentValue));
    }

    /*
        * **Private Methods**
        * */
    private void incrementValue()
    {
        mCurrentValue = mCurrentValue + mIncValue;
        if(mCurrentValue > mMaxValue)
        {
            mCurrentValue = mMinValue;
        }
        mNumberTextView.setText(mCurrentValue + "");
    }

    private void decrementValue()
    {
        mCurrentValue = mCurrentValue - mIncValue;
        if(mCurrentValue < mMinValue)
        {
            mCurrentValue = mMaxValue;
        }
        mNumberTextView.setText(mCurrentValue + "");
    }

}
