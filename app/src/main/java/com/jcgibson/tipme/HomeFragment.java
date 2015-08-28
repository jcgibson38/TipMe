package com.jcgibson.tipme;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jordan on 7/20/2015.
 *
 * This is the home fragment holding the Calendar, to be hosted by HomeActivity.
 */
public class HomeFragment extends Fragment
{
    private static final String MODE_ADD = "ADD";

    //View elements.
    private MaterialCalendarView mCalendarView;
    private View v;

    //Member variables.
    private Date mDate;
    private Calendar mCalendar;
    private OnDateSelectedListener mListener;
    private String mMode;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Initialize variables.
        mCalendar = Calendar.getInstance();
        mDate = mCalendar.getTime();
        mMode = MODE_ADD;

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_home, parent, false);

        setShiftDetails(mDate);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tip Me");
        }

        mCalendarView = (MaterialCalendarView)v.findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangedListener(new OnDateChangedListener()
        {
            @Override
            public void onDateChanged(@NonNull MaterialCalendarView materialCalendarView, CalendarDay calendarDay)
            {
                mCalendar = calendarDay.getCalendar();
                mDate = mCalendar.getTime();
                mListener.onDateSelected(mDate);
                setShiftDetails(mDate);
            }
        });

        //Decorate the calendar.
        decorate();

        return v;
    }

    /*
    * The method to decorate the Calendar View.
    * */
    public void decorate()
    {
        DayViewDecorator decorator = new DayViewDecorator()
        {
            //Return true if Date should be decorated, or false if not.
            @Override
            public boolean shouldDecorate(CalendarDay calendarDay)
            {
                Shift shift = ShiftRegister.get(getActivity()).lookupShift(calendarDay.getDate());
                if(shift != null)
                {
                    return true;
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade dayViewFacade)
            {
                Drawable drawable = getResources().getDrawable(R.drawable.circle);
                dayViewFacade.setBackgroundDrawable(drawable);
            }
        };

        mCalendarView.addDecorator(decorator);
    }

    public void deleteDecorations()
    {
        mCalendarView.removeDecorators();
    }

    /*
    * Checks whether the date selected in the CalendarView has a corresponding Shift logged,
    * and displays the necessary fragment in the detail view.
    *
    * @param date -> The date selected in the Calendar.
    * */
    public void setShiftDetails(Date date)
    {
        Shift shift = ShiftRegister.get(getActivity()).lookupShift(date);

        if(shift != null)
        {
            //Callback to update Activity.
            mListener.onShiftFound(mDate, shift);

            //Shift was found for this Date, so show HomeDetailFragmentFull.
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.home_detail_fragment_container);
            if(f == null)
            {
                f = new HomeDetailFragmentFull();
                fm.beginTransaction().add(R.id.home_detail_fragment_container, f, FragmentCheck.HOME_DETAIL_FRAGMENT_FULL).commit();
            }
            else
            {
                FragmentCheck.changeDetailFragment(fm, new HomeDetailFragmentFull(), FragmentCheck.HOME_DETAIL_FRAGMENT_FULL);
            }
        }
        else
        {
            //No shift for this Date so show HomeDetailFragmentEmpty.
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.home_detail_fragment_container);
            if(f == null)
            {
                f = new HomeDetailFragmentEmpty();
                fm.beginTransaction().add(R.id.home_detail_fragment_container, f).commit();
            }
            else
            {
                FragmentCheck.changeDetailFragment(fm, new HomeDetailFragmentEmpty(), FragmentCheck.HOME_FRAGMENT_TAG);
            }
        }
    }

        /*
    **Interface**
     */

    public interface OnDateSelectedListener
    {
        void onShiftFound(Date date, Shift shift);
        void onDateSelected(Date date);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mListener = (OnDateSelectedListener) activity;
    }
}
