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
 *
 * This is the Class that holds all of the registered Shifts.
 */
public class ShiftRegister
{
    private static ShiftRegister sShiftRegister;

    private Context mContext;

    private List<Shift> mShifts;

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
            mShifts = db.getAllShifts();
        }
        else
        {
            mShifts = new ArrayList<>();
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

    public List<Shift> getShifts()
    {
        return mShifts;
    }

    /*
    * Setters
    * */

    /*
    * Other Methods
    * */

    public void addShift(Shift shift)
    {
        mShifts.add(shift);
    }

    public void deleteShift(Shift shift)
    {
        mShifts.remove(shift);
    }

    /*
    * This method looks through the ShiftRegister for a Shift that matches the specified date.
    *
    * @param date -> The date to compare to the dates of the Shifts in the ShiftRegister looking for a matching date.
    * */
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
}
