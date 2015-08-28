package com.jcgibson.tipme;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
 * This is the home fragment to be hosted by HomeActivity.
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
            public void onDateChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay)
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

    public void decorate()
    {
        DayViewDecorator decorator = new DayViewDecorator()
        {
            @Override
            public boolean shouldDecorate(CalendarDay calendarDay)
            {
                if((ShiftRegister.get(getActivity()).lookupShiftByDate(calendarDay.getDate())) != null)
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

    //Checks whether the date selected in the CalendarView has a shift logged, and displays the necessary fragment for the detail view.
    public void setShiftDetails(Date date)
    {
        Shift shift;
        if((shift = ShiftRegister.get(getActivity()).lookupShiftByDate(date)) != null)
        {
            mListener.onShiftFound(mDate, shift);

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

    /*
    * **Options Menu**
    * */
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            //Switch to delete mode.
            case R.id.menu_item_delete:
                //Change the FloatingActionButton to 'trash' and change the actionbar as needed.
                mMode = MODE_DELETE;
                //mAddShiftButton.setImageResource(R.drawable.ic_delete_white_24dp);
                if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
                {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Delete Shift");
                }

                return true;
            case R.id.menu_item_add:
                //Change the FloatingActionButton to 'plus' and change the actionbar as needed.
                mMode = MODE_ADD;
                //mAddShiftButton.setImageResource(android.R.drawable.ic_input_add);
                if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
                {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tip Me");
                }

                return true;
            case android.R.id.home:
                //Change the FloatingActionButton to 'plus' and change the actionbar as needed for 'Home' mode.
                mMode = MODE_ADD;
                //mAddShiftButton.setImageResource(android.R.drawable.ic_input_add);
                if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
                {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tip Me");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}
