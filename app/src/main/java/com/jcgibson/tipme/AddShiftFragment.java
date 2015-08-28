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
 * Created by Jordan on 7/20/2015.
 *
 * This is the fragment displayed for a user to add their shift report for a given day.
 */
public class AddShiftFragment extends Fragment
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

    private Date mDate;
    private Shift mShift;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Enable options menu.
        setHasOptionsMenu(true);

        //Initialize Variables.
        mShift = new Shift();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_shift_fragment, parent, false);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Shift");
        }

        mCurrencyCashTextView = (TextView)v.findViewById(R.id.cashCurrencyTextView);
        mCurrencyCashTextView.setTextColor(Color.parseColor("#80FFFFFF"));

        //Get Cash Tips.
        cashTips = "0";
        mCashTipsEditText = (EditText)v.findViewById(R.id.cash_tips_editText);
        mCashTipsEditText.addTextChangedListener(new TextWatcher()
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
                    mCurrencyCashTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                } else
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

        mCurrencyCreditTextView = (TextView)v.findViewById(R.id.creditCurrencyTextView);
        mCurrencyCreditTextView.setTextColor(Color.parseColor("#80FFFFFF"));

        //Get Credit Tips.
        creditTips = "0";
        mCreditTipsEditText = (EditText)v.findViewById(R.id.credit_tips_editText);
        mCreditTipsEditText.addTextChangedListener(new TextWatcher()
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
                    mCurrencyCreditTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                } else
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

        mCurrencyTipOutTextView = (TextView)v.findViewById(R.id.tipOutTextView);
        mCurrencyTipOutTextView.setTextColor(Color.parseColor("#80FFFFFF"));

        //Get Tip Out.
        tipOut = "0";
        mTipOutEditText = (EditText)v.findViewById(R.id.tip_out_editText);
        mTipOutEditText.addTextChangedListener(new TextWatcher()
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
                    mCurrencyTipOutTextView.setTextColor(Color.parseColor("#80FFFFFF"));
                } else
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

        //Get Hours Worked.
        mHoursWorkedNumberPicker = (com.jcgibson.customwidgets.NumberPicker)v.findViewById(R.id.hoursWorkedPicker);
        mMinutesWorkedNumberPicker = (com.jcgibson.customwidgets.NumberPicker)v.findViewById(R.id.minutesWorkedPicker);


        //Get Date.
        mDateButton = (Button)v.findViewById(R.id.tipDateButton);
        mDateButton.setText(HomeActivity.getDateAsString());
        mDate = HomeActivity.getDate();
        mDateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Launch DatePickerFragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mShift.getDate());
                dialog.setTargetFragment(AddShiftFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mCurrencySalesTextView = (TextView)v.findViewById(R.id.totalSalesCurrencyTextView);
        mCurrencySalesTextView.setTextColor(Color.parseColor("#80FFFFFF"));

        //Get Total Sales.
        totalSales = "0";
        mSalesEditText = (EditText)v.findViewById(R.id.totalSalesData);
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

        //Inflate options menu.
        inflater.inflate(R.menu.accept_cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                //Revert to HomeFragment.
                FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new HomeFragment(), FragmentCheck.HOME_FRAGMENT_CALENDAR);
                return true;
            case R.id.accept_button:
                //Set new shifts values and calculate.
                if(cashTips.equals(""))
                {
                    mShift.setCashTips(0.00);
                }
                else
                {
                    mShift.setCashTips(Double.parseDouble(cashTips));
                }
                if(creditTips.equals(""))
                {
                    mShift.setCreditTips(0.00);
                }
                else
                {
                    mShift.setCreditTips(Double.parseDouble(creditTips));
                }
                if(tipOut.equals(""))
                {
                    mShift.setTipOut(0.00);
                }
                else
                {
                    mShift.setTipOut(Double.parseDouble(tipOut));
                }
                if(totalSales.equals(""))
                {
                    mShift.setTotalSales(0.00);
                }
                else
                {
                    mShift.setTotalSales(Double.parseDouble(totalSales));
                }
                mShift.setDate(mDate);

                //Get time worked.
                double whole = mHoursWorkedNumberPicker.getCurrentValue();
                double part = mMinutesWorkedNumberPicker.getCurrentValue();
                double decimal = part / 60;
                mShift.setHoursWorked(whole + decimal);

                //Add the new shift to the shift register.
                ShiftRegister.get(getActivity()).addShift(mShift);

                //Save the shift to database.
                DatabaseHandler db = new DatabaseHandler(getActivity());
                long shiftId = db.addShift(mShift);
                mShift.setId(shiftId);

                //Revert to the home fragment.
                FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new HomeFragment(), FragmentCheck.HOME_FRAGMENT_CALENDAR);
                return true;
            case R.id.cancel_button:
                //Revert to HomeFragment.
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
            //Get date returned by DatePickerFragment.
            mDate = (Date)data.getSerializableExtra(FragmentCheck.EXTRA_DATE_TAG);
            updateDate();
        }
    }

    public void updateDate()
    {
        mDateButton.setText(mShift.getDateAsString());
    }
}
