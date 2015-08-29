package com.jcgibson.tipme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcgibson.graphwidgets.PieChart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 8/22/2015.
 */
public class WeeklyDetailFragment extends Fragment
{
    private Shift mCurrentShift;
    private List<Shift> mWeekShifts;

    private double mWeeksCashTips;
    private double mWeeksCreditTips;
    private double mWeeksTipOut;
    private double mTimeWorked;

    private PieChart mPieChart;
    private TextView mCashTipsValue;
    private TextView mCreditTipsValue;
    private TextView mTipOutValue;
    private TextView mTotalTipsValue;
    private TextView mDaysWorkedValue;
    private TextView mHourlyWageValue;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCurrentShift = ((DetailActivity)getActivity()).getCurrentShift();
        mWeekShifts = new ArrayList<>();
        mWeeksCashTips = 0;
        mWeeksCreditTips = 0;
        mWeeksTipOut = 0;
        mTimeWorked = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_detail_weekly, parent, false);

        //Get the weeks shifts, and find the total values for PieChart.
        mWeekShifts = ShiftRegister.get(getActivity()).getWeekShifts(mCurrentShift);
        for(Shift s : mWeekShifts)
        {
            mWeeksCashTips += s.getCashTips();
            mWeeksCreditTips += s.getCreditTips();
            mWeeksTipOut += s.getTipOut();
            mTimeWorked += s.getHoursWorked();
        }

        //Set up PieChart.
        mPieChart = (PieChart)v.findViewById(R.id.pieChart);
        mPieChart.addSlice(Color.parseColor("#F06292"), (float) mWeeksCashTips, "temp1");
        mPieChart.addSlice(Color.parseColor("#2196F3"), (float) mWeeksCreditTips, "Temp2");
        mPieChart.addSlice(Color.parseColor("#DCE775"), (float) mWeeksTipOut, "Temp3");

        //Display total tips.
        mTotalTipsValue = (TextView)v.findViewById(R.id.totalTipsValue);
        mTotalTipsValue.setText(formatValue((mWeeksCashTips + mWeeksCreditTips) - mWeeksTipOut));

        //Display total days worked.
        mDaysWorkedValue = (TextView)v.findViewById(R.id.daysWorkedValue);
        mDaysWorkedValue.setText("" + mWeekShifts.size());

        //Display hourly wage.
        mHourlyWageValue = (TextView)v.findViewById(R.id.hourlyRateValue);
        mHourlyWageValue.setText("$" + formatValue(((mWeeksCashTips + mWeeksCreditTips) - mWeeksTipOut) / mTimeWorked));

        //Display cash tips.
        mCashTipsValue = (TextView)v.findViewById(R.id.cashTipsValue);
        mCashTipsValue.setText(formatValue(mWeeksCashTips));

        //Display credit tips.
        mCreditTipsValue = (TextView)v.findViewById(R.id.creditTipsValue);
        mCreditTipsValue.setText(formatValue(mWeeksCreditTips));

        //Display tip out.
        mTipOutValue = (TextView)v.findViewById(R.id.tipoutTipsValue);
        mTipOutValue.setText(formatValue(mWeeksTipOut));

        return v;
    }

    /*
    * Method to format a double value to be displayed as a dollar amount.
    *
    * @param value -> The value to be formatted.
    * */
    public String formatValue(Double value)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }
}
