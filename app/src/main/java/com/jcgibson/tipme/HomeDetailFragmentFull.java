package com.jcgibson.tipme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Jordan on 7/20/2015.
 */
public class HomeDetailFragmentFull extends Fragment
{
    public final static String DAILY_DETAIL_EXTRA = "com.jcgibson.tipme.dailydetailextra";

    private TextView mTodayTipsMadeTextView;
    private TextView mTodayLabelTextView;
    private TextView mTodayHoursWorkedTextView;
    private TextView mHourlyWageTextView;
    private TextView mPercentOfSalesTextView;

    private OnDeleteListener mListener;

    private PopupMenu mPopupMenu;
    private ImageView mMenu;

    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_home_detail_full, parent, false);

        /*
        * **Popup Menu**
        * */

        //Register popup menu.
        mPopupMenu = new PopupMenu(getActivity(), v.findViewById(R.id.popup_menu_button));
        mMenu = (ImageView)v.findViewById(R.id.popup_menu_button);
        MenuInflater menuInflater = mPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, mPopupMenu.getMenu());
        mMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPopupMenu.show();
            }
        });

        //Handle clicks on menu items.
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.menu_item_edit:
                        //Switch to the edit shift fragment.
                        FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new EditShiftFragment(), FragmentCheck.ADD_SHIFT_TAG);
                        return true;
                    case R.id.menu_item_delete:
                        //Shift to be deleted.
                        Shift shift = HomeActivity.getShift();
                        mListener.onShiftDeleted(shift);

                        //Delete the shift from the database.
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        db.deleteShift(shift);
                        db.close();

                        //Delete the shift from the shiftRegister.
                        ShiftRegister.get(getActivity()).deleteShift(shift);

                        //Redecorate the calendar.
                        mListener.onRedecorateListener();

                        //Revert detail fragment to empty fragment.
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        Fragment f = fm.findFragmentById(R.id.home_detail_fragment_container);
                        FragmentCheck.changeDetailFragment(fm, new HomeDetailFragmentEmpty(), FragmentCheck.HOME_FRAGMENT_TAG);

                        return true;
                    case R.id.menuItemDetails:
                        //Switch to detail activity.
                        Intent i = new Intent(getActivity(), DetailActivity.class);
                        i.putExtra(DAILY_DETAIL_EXTRA, HomeActivity.getShift());
                        startActivity(i);
                        return true;
                    default:
                        return true;
                }
            }
        });

        /*
        * **Display**
        * */

        //Display today's date.
        mTodayLabelTextView = (TextView)v.findViewById(R.id.dateValueTextView);
        mTodayLabelTextView.setText("" + HomeActivity.getShift().getDateAsString());

        //Display tips made Today.
        mTodayTipsMadeTextView = (TextView)v.findViewById(R.id.totalValueTextView);
        mTodayTipsMadeTextView.setText(HomeActivity.getShift().displayTotalTips());

        //Display hours worked Today.
        mTodayHoursWorkedTextView = (TextView)v.findViewById(R.id.hoursWorkedValueTextView);
        mTodayHoursWorkedTextView.setText(HomeActivity.getShift().displayHoursWorked());

        //Display hourly wage.
        mHourlyWageTextView = (TextView)v.findViewById(R.id.hourlyWageValueTextView);
        mHourlyWageTextView.setText("$" + HomeActivity.getShift().displayHourlyWage());

        //Display percent of sales.
        mPercentOfSalesTextView = (TextView)v.findViewById(R.id.percenOfSalesData);
        mPercentOfSalesTextView.setText(HomeActivity.getShift().displayPercentOfSales() + "%");

        return v;
    }

    public interface OnDeleteListener
    {
        void onShiftDeleted(Shift shift);
        void onRedecorateListener();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mListener = (OnDeleteListener) activity;
    }
}
