package com.jcgibson.tipme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Jordan on 7/25/2015.
 */
public class EditShiftFragment extends Fragment
{
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "date";

    private TextView mCurrencyCashTextView;
    private TextView mCurrencyCreditTextView;
    private TextView mCurrencyTipOutTextView;
    private TextView mCurrencySalesTextView;
    private EditText mCashTipsEditText;
    private EditText mCreditTipsEditText;
    private EditText mTipOutEditText;
    private EditText mSalesEditText;
    private Button mDateButton;
    private com.jcgibson.customwidgets.NumberPicker mHoursWorkedNumberPicker;
    private com.jcgibson.customwidgets.NumberPicker mMinutesWorkedNumberPicker;

    private String cashTips;
    private String creditTips;
    private String tipOut;
    private String totalSales;

    private Date date;
    private double hoursWorked;
    private int minutesWorked;
    private Shift mShift;

    HomeDetailFragmentFull.OnDeleteListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        //Load the selected shift.
        mShift = HomeActivity.getShift();
        cashTips = mShift.displayCashTips();
        creditTips = mShift.displayCreditTips();
        tipOut = mShift.displayTipOut();
        totalSales = mShift.displayTotalSales();
        hoursWorked = mShift.getHoursWorked();
        minutesWorked = mShift.getMinutesWorked();
        date = mShift.getDate();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mListener = (HomeDetailFragmentFull.OnDeleteListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_shift_fragment, parent, false);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Shift");
        }

        //Edit cash tips.
        mCurrencyCashTextView = (TextView)v.findViewById(R.id.cashCurrencyTextView);
        mCurrencyCashTextView.setTextColor(Color.parseColor("#FFFFFFFF"));

        mCashTipsEditText = (EditText)v.findViewById(R.id.cash_tips_editText);
        mCashTipsEditText.setText(cashTips);
        mCashTipsEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length() == 0)
                {
                    mCurrencyCashTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                }
                else
                {
                    mCurrencyCashTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                cashTips = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Edit credit tips.
        mCurrencyCreditTextView = (TextView)v.findViewById(R.id.creditCurrencyTextView);
        mCurrencyCreditTextView.setTextColor(Color.parseColor("#FFFFFFFF"));

        mCreditTipsEditText = (EditText)v.findViewById(R.id.credit_tips_editText);
        mCreditTipsEditText.setText(creditTips);
        mCreditTipsEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length() == 0)
                {
                    mCurrencyCreditTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                }
                else
                {
                    mCurrencyCreditTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                creditTips = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Edit tip out.
        mCurrencyTipOutTextView = (TextView)v.findViewById(R.id.tipOutTextView);
        mCurrencyTipOutTextView.setTextColor(Color.parseColor("#FFFFFFFF"));

        mTipOutEditText = (EditText)v.findViewById(R.id.tip_out_editText);
        mTipOutEditText.setText(tipOut);
        mTipOutEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length() == 0)
                {
                    mCurrencyTipOutTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                }
                else
                {
                    mCurrencyTipOutTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                tipOut = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        //Edit hours worked.
        mHoursWorkedNumberPicker = (com.jcgibson.customwidgets.NumberPicker)v.findViewById(R.id.hoursWorkedPicker);
        mHoursWorkedNumberPicker.setCurrentValue((int) hoursWorked);
        mMinutesWorkedNumberPicker = (com.jcgibson.customwidgets.NumberPicker)v.findViewById(R.id.minutesWorkedPicker);
        mMinutesWorkedNumberPicker.setCurrentValue(minutesWorked);

        //Edit date.
        mDateButton = (Button)v.findViewById(R.id.tipDateButton);
        mDateButton.setText(mShift.getDateAsString());
        mDateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Launch DatePickerFragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mShift.getDate());
                dialog.setTargetFragment(EditShiftFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        //Edit Total Sales.
        mCurrencySalesTextView = (TextView)v.findViewById(R.id.totalSalesCurrencyTextView);
        mCurrencySalesTextView.setTextColor(Color.parseColor("#FFFFFFFF"));

        mSalesEditText = (EditText)v.findViewById(R.id.totalSalesData);
        mSalesEditText.setText(totalSales);
        mSalesEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 0)
                {
                    mCurrencySalesTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                } else
                {
                    mCurrencySalesTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                totalSales = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        return v;
    }

    /*
    * **Toolbar**
    * */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.accept_cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new HomeFragment(), FragmentCheck.HOME_FRAGMENT_CALENDAR);
                return true;
            case R.id.accept_button:
                //Find the old shift.
                Shift shift = HomeActivity.getShift();

                //Delete the shift from the database.
                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.deleteShift(shift);
                db.close();

                //Delete the shift from the shiftRegister.
                ShiftRegister.get(getActivity()).deleteShift(shift);

                //Set new shifts values and calculate.
                mShift.finalizeShift(cashTips,creditTips, tipOut, totalSales, date, (double)mHoursWorkedNumberPicker.getCurrentValue(), (double)mMinutesWorkedNumberPicker.getCurrentValue());

                //Edit the shift in the shiftregister.
                ShiftRegister.get(getActivity()).updateShift(mShift);

                //Edit the shift in the database.
                db.updateShift(mShift);
                db.close();

                //Revert to the home fragment.
                FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new HomeFragment(), FragmentCheck.HOME_FRAGMENT_CALENDAR);

                return true;
            case R.id.cancel_button:
                FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new HomeFragment(), FragmentCheck.HOME_FRAGMENT_CALENDAR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    * **Activity Result**
    * */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode == REQUEST_DATE)
        {
            date = (Date)data.getSerializableExtra(FragmentCheck.EXTRA_DATE_TAG);
            updateDate();
        }
    }

    public void updateDate()
    {
        mDateButton.setText(mShift.getDateAsString());
    }
}
