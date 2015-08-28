package com.jcgibson.tipme;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jordan on 7/20/2015.
 *
 * This is the Shift class which represents information about a given shift worked.
 */
public class Shift implements Parcelable
{
    private double mCashTips;
    private double mCreditTips;
    private double mTipOut;
    private double mHoursWorked;
    private double mTotalSales;
    private double mPercentOfSales;
    private double mTotalTips;
    private Date mDate;
    private int _id;

    /*
    * Blank constructor.
    *
    * Create a Shift with all variables set to defaults.
    * */
    public Shift()
    {
        //Loadable values.
        mCashTips = 0;
        mCreditTips = 0;
        mTipOut = 0;
        mDate = new Date();
        mHoursWorked = 0;
        mTotalSales = 0;

        //Calculable values.
        mPercentOfSales = 0;
        mTotalTips = 0;
    }

    public Shift(Cursor cursor)
    {
        //Loadable values.
        _id = cursor.getInt(0);
        mCashTips = cursor.getDouble(1);
        mCreditTips = cursor.getDouble(2);
        mTipOut = cursor.getDouble(3);
        mHoursWorked = cursor.getDouble(4);
        mDate = new Date(cursor.getLong(5));
        mTotalSales = cursor.getDouble(6);

        //Calculable values.
        calculatePercentOfSales();
        calculateTotalTips();
    }

    /*
    * Methods for calculating data based on user supplied information.
    *
    *   Percent Of Sales (mPercentOfSales)
    *   Total Tips (mTotalTips)
    *
    * */

    private void calculatePercentOfSales()
    {
        mPercentOfSales = (mTotalTips / mTotalSales) * 100;
    }

    private void calculateTotalTips()
    {
        mTotalTips = (mCashTips + mCreditTips) - mTipOut;
    }

    /*
    * Getters
    * */

    public double getPercentOfSales()
    {
        calculatePercentOfSales();
        return mPercentOfSales;
    }

    public double getTotalTips()
    {
        calculateTotalTips();
        return mTotalTips;
    }

    public double getTotalSales()
    {
        return mTotalSales;
    }

    public double getCashTips()
    {
        return mCashTips;
    }

    public double getCreditTips()
    {
        return mCreditTips;
    }

    public double getTipOut()
    {
        return mTipOut;
    }

    public Date getDate()
    {
        return mDate;
    }

    public double getHoursWorked()
    {
        return mHoursWorked;
    }

    public int getMinutesWorked()
    {
        double minutes = mHoursWorked - ((int)mHoursWorked);
        return (int)(minutes * 60);
    }

    public Double getHourlyWage()
    {
        return ((mCashTips + mCreditTips) - mTipOut) / mHoursWorked;
    }

    public int getId()
    {
        return _id;
    }

    /*
    * Setters
    * */

    public void setPercentOfSales(double percentOfSales)
    {
        mPercentOfSales = percentOfSales;
    }

    public void setTotalTips(double totalTips)
    {
        mTotalTips = totalTips;
    }

    public void setTotalSales(double totalSales)
    {
        mTotalSales = totalSales;
    }

    public void setCashTips(double cashTips)
    {
        mCashTips = cashTips;
        calculateTotalTips();
    }

    public void setCreditTips(double creditTips)
    {
        mCreditTips = creditTips;
        calculateTotalTips();
    }

    public void setTipOut(double tipOut)
    {
        mTipOut = tipOut;
        calculateTotalTips();
    }

    public void setDate(Date mDate)
    {
        this.mDate = mDate;
    }

    public void setHoursWorked(double mHoursWorked)
    {
        this.mHoursWorked = mHoursWorked;
    }

    public void setId(long id)
    {
        _id = Integer.parseInt(Long.toString(id));
    }

    /*
    * Other Methods.
    * */

    public String getDateAsString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(mDate);
    }

    public Long getDateAsLong()
    {
        return mDate.getTime();
    }

    public String displayTotalTips()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format((mCashTips + mCreditTips) - mTipOut);
    }

    public String displayHoursWorked()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mHoursWorked);
    }

    public String displayHourlyWage()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(((mCashTips + mCreditTips) - mTipOut) / mHoursWorked);
    }

    public String displayCashTips()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mCashTips);
    }

    public String displayCreditTips()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mCreditTips);
    }

    public String displayTipOut()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mTipOut);
    }

    public String displayTotalSales()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mTotalSales);
    }

    public String displayPercentOfSales()
    {
        calculatePercentOfSales();
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mPercentOfSales);
    }

    /*
    * Parcelable interface
    * */

    //Create Shift from Parcel.
    public Shift(Parcel in)
    {
        mCashTips = in.readDouble();
        mCreditTips = in.readDouble();
        mTipOut = in.readDouble();
        mHoursWorked = in.readDouble();
        mTotalSales = in.readDouble();
        mDate = new Date(in.readLong());
        _id = in.readInt();

        calculatePercentOfSales();
        calculateTotalTips();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    //Package Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDouble(mCashTips);
        dest.writeDouble(mCreditTips);
        dest.writeDouble(mTipOut);
        dest.writeDouble(mHoursWorked);
        dest.writeDouble(mTotalSales);
        dest.writeLong(mDate.getTime());
        dest.writeInt(_id);
    }

    //Parcel creator.
    public static final Creator<Shift> CREATOR = new Creator<Shift>()
    {
        @Override
        public Shift createFromParcel(Parcel source)
        {
            return new Shift(source);
        }

        @Override
        public Shift[] newArray(int size)
        {
            return new Shift[size];
        }
    };
}
