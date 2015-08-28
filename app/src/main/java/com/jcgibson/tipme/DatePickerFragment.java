package com.jcgibson.tipme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jordan on 7/20/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    private Date mDate;

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(FragmentCheck.EXTRA_DATE_TAG, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
        getArguments().putSerializable(FragmentCheck.EXTRA_DATE_TAG, mDate);

        sendResult(Activity.RESULT_OK);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mDate = (Date)getArguments().getSerializable(FragmentCheck.EXTRA_DATE_TAG);

        //Get the date of the transaction and set the calendar object to it.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        //Get the date values.
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    private void sendResult(int resultCode)
    {
        if(getTargetFragment() == null)
        {
            return;
        }
        Intent i = new Intent();
        i.putExtra(FragmentCheck.EXTRA_DATE_TAG, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
