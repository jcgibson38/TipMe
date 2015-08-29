package com.jcgibson.tipme;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Jordan on 7/20/2015.
 *
 * This is the Class that holds all of the registered Shifts.
 */
public class ShiftRegister
{
    private static ShiftRegister sShiftRegister;

    private Context mContext;

    private Map<Date, Shift> mShiftMap;

    /*
    * ShiftRegister constructor.
    * */
    private ShiftRegister(Context context)
    {
        mContext = context;

        //Load shifts from database, or create a blank List.
        DatabaseHandler db = new DatabaseHandler(context);
        if(db.getShiftsCount() != 0)
        {
            mShiftMap = db.getAllMapShifts();
        }
        else
        {
            mShiftMap = new android.support.v4.util.ArrayMap<>();
        }
        db.close();
    }

    /*
    * Get the static ShiftRegister.
    * */
    public static ShiftRegister get(Context context)
    {
        if(sShiftRegister == null)
        {
            sShiftRegister = new ShiftRegister(context.getApplicationContext());
        }
        return sShiftRegister;
    }

    /*
    * Getters
    * */

    public Map<Date, Shift> getShifts()
    {
        return mShiftMap;
    }

    /*
    * Setters
    * */

    /*
    * Other Methods
    * */

    public void addShift(Shift shift)
    {
        mShiftMap.put(shift.getDate(), shift);
    }

    public void deleteShift(Shift shift)
    {
        mShiftMap.remove(shift.getDate());
    }

    /*
    * This method looks up a Shift that matches the specified date.
    *
    * @param date -> The date to compare to the dates of the Shifts in the ShiftRegister looking for a matching date.
    * */
    public Shift lookupShift(Date date)
    {
        return mShiftMap.get(date);
    }

    /*
    * This method is meant to 'update' a shift already in the map.
    *
    * @param shift -> The shift that needs to be updated.
    * */
    public void updateShift(Shift shift)
    {
        mShiftMap.remove(shift.getDate());
        mShiftMap.put(shift.getDate(), shift);
    }

    /*
    * This method returns a List of Shifts corresponding to the Shifts in the same week as @param shift.
    *
    * @param shift -> The shift for which to look for other shifts of the same week.
    * */
    public List<Shift> getWeekShifts(Shift shift)
    {
        List<Shift> shifts = new ArrayList<>();

        //Get the week of interest.
        Calendar calOfShift = Calendar.getInstance();
        calOfShift.setTime(shift.getDate());
        Calendar weekCal = calOfShift;

        //Get shifts earlier in the week.
        while(calOfShift.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            weekCal.add(Calendar.DAY_OF_WEEK, -1);
            Shift s = mShiftMap.get(weekCal.getTime());
            if(s != null)
            {
                shifts.add(s);
            }
        }

        //Get shifts later in the week.
        while(calOfShift.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
        {
            weekCal.add(Calendar.DAY_OF_WEEK, 1);
            Shift s = mShiftMap.get(weekCal.getTime());
            if(s != null)
            {
                shifts.add(s);
            }
        }

        for(Shift s : shifts)
        {
            Log.d("TAG", "Shift " + s.getDateAsString());
        }

        return shifts;
    }
}
