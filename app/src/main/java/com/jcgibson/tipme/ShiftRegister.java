package com.jcgibson.tipme;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Jordan on 7/20/2015.
 */
public class ShiftRegister
{
    private static ShiftRegister sShiftRegister;
    private Context mContext;
    private List<Shift> mShifts;

    private ShiftRegister(Context context)
    {
        mContext = context;
        DatabaseHandler db = new DatabaseHandler(context);

        Log.d("COUNT", "Count is " + db.getShiftsCount());
        if(db.getShiftsCount() != 0)
        {
            mShifts = db.getAllShifts();
            Log.d("DBLOG", "Shifts loaded from database into ShiftRegister.");
        }
        else
        {
            mShifts = new ArrayList<>();
            Log.d("DBLOG", "No shifts found, creating new ShiftRegister.");
        }
        db.close();
    }

    public static ShiftRegister get(Context context)
    {
        if(sShiftRegister == null)
        {
            sShiftRegister = new ShiftRegister(context.getApplicationContext());
        }
        return sShiftRegister;
    }

    public List<Shift> getShifts()
    {
        return mShifts;
    }

    public void addShift(Shift shift)
    {
        mShifts.add(shift);
    }

    public void deleteShift(Shift shift)
    {
        mShifts.remove(shift);
    }

    public Shift lookupShiftByDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for(Shift s : mShifts)
        {
            Calendar scal = Calendar.getInstance();
            scal.setTime(s.getDate());

            if((cal.get(Calendar.YEAR) == scal.get(Calendar.YEAR)) && (cal.get(Calendar.MONTH) == scal.get(Calendar.MONTH)) && (cal.get(Calendar.DAY_OF_MONTH) == scal.get(Calendar.DAY_OF_MONTH)))
            {
                return s;
            }
        }
        return null;
    }

    public void updateShift(Shift shift)
    {
        for(Shift s : mShifts)
        {
            if(s.getId() == shift.getId())
            {
                s = shift;
            }
        }
    }


    public List<Shift> getWeeksShifts(Shift shift, List<Shift> shifts)
    {
        List<Shift> mList = new ArrayList<>();
        Date date = shift.getDate();
        Date weekStart;
        Date weekEnd;

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDate();

        Log.d("TAG", "Given shift is " + date.toString());
        Log.d("TAG", "day " + day);
        GregorianCalendar g = new GregorianCalendar(year + 1900, month, day);
        Log.d("TAG", "Given shift is " + g.getTime().toString());
        while(g.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            //Roll to sunday
            g.roll(Calendar.DAY_OF_WEEK, false);
            Log.d("TAG", "Roll " + g.getTime().toString());
        }
        weekStart = g.getTime();
        Log.d("TAG", "WeekStart " + weekStart.toString());
        g.add(Calendar.DAY_OF_WEEK, 6);
        weekEnd = g.getTime();
        Log.d("TAG", "weekEnd " + weekEnd.toString());

        for(Shift s : mShifts)
        {
            if(s.getDate().compareTo(weekEnd) <=0  && (s.getDate().compareTo(weekStart) >= 0))
            {
                mList.add(s);
            }
        }
        Log.d("TAG", "Found " + mList.size() + " shifts this week.");
        return mList;
    }
}
