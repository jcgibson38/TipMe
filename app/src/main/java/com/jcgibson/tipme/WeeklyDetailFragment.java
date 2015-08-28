package com.jcgibson.tipme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 8/22/2015.
 */
public class WeeklyDetailFragment extends Fragment
{
    private Shift mCurrentShift;
    private List<Shift> mWeekShifts;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCurrentShift = ((DetailActivity)getActivity()).getCurrentShift();
        mWeekShifts = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_detail_weekly, parent, false);

        //Get all of the shifts from this week.
        mWeekShifts = ShiftRegister.get(getActivity()).getWeeksShifts(mCurrentShift, ShiftRegister.get(getActivity()).getShifts());

        return v;
    }
}
