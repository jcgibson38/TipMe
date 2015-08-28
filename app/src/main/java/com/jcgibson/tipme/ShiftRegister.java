package com.jcgibson.tipme;

import android.content.Context;

import java.util.Date;
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

    public void updateShift(Shift shift)
    {
        mShiftMap.remove(shift.getDate());
        mShiftMap.put(shift.getDate(), shift);
    }
}
