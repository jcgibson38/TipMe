package com.jcgibson.tipme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcgibson.graphwidgets.PieChart;

/**
 * Created by Jordan on 8/19/2015.
 */
public class DailyDetailFragment extends Fragment
{
    private PieChart mPieChart;

    private TextView mCashTipsTextView;
    private TextView mCreditTipsTextView;
    private TextView mTipOutTextView;
    private TextView mTotalTipsTextView;
    private TextView mPercentOfSalesTextView;
    private TextView mTotalSalesTextView;

    private Shift mShift;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mShift = ((DetailActivity)getActivity()).getCurrentShift();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_detail_daily, parent, false);

        //PieChart
        mPieChart = (PieChart)v.findViewById(R.id.pieChart);
        mPieChart.addSlice(Color.parseColor("#F06292"), (float)mShift.getCashTips(), "Temp1");
        mPieChart.addSlice(Color.parseColor("#2196F3"), (float)mShift.getCreditTips(), "Temp2");
        mPieChart.addSlice(Color.parseColor("#DCE775"), (float) mShift.getTipOut(), "Temp3");

        //Cash tips.
        mCashTipsTextView = (TextView)v.findViewById(R.id.cashTipsValue);
        mCashTipsTextView.setText("$" + mShift.displayCashTips());

        //Credit tips.
        mCreditTipsTextView = (TextView)v.findViewById(R.id.creditTipsValue);
        mCreditTipsTextView.setText("$" + mShift.displayCreditTips());

        //Tip out.
        mTipOutTextView = (TextView)v.findViewById(R.id.tipoutTipsValue);
        mTipOutTextView.setText("$" + mShift.displayTipOut());

        //Total tips.
        mTotalTipsTextView = (TextView)v.findViewById(R.id.totalTipsValue);
        mTotalTipsTextView.setText(mShift.displayTotalTips());

        //Percent of sales.
        mPercentOfSalesTextView = (TextView)v.findViewById(R.id.percentOfSalesValue);
        mPercentOfSalesTextView.setText(mShift.displayPercentOfSales() + "%");

        //Total sales.
        mTotalSalesTextView = (TextView)v.findViewById(R.id.totalSalesValue);
        mTotalSalesTextView.setText("$" + mShift.displayTotalSales());

        return v;
    }
}
