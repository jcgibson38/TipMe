package com.jcgibson.tipme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Jordan on 7/20/2015.
 */
public class FragmentCheck
{
    public static final String ADD_SHIFT_TAG = "ADD_SHIFT";
    public static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT";
    public static final String HOME_DETAIL_FRAGMENT_FULL = "HOME_DETAIL_FRAGMENT_FULL";
    public static final String HOME_FRAGMENT_CALENDAR = "HOME_FRAGMENT_CALENDAR";
    public static final String DAY_DETAIL_FRAGMENT = "DAY_DETAIL_FRAGMENT";
    public static final String WEEK_DETAIL_FRAGMENT = "WEEK_DETAIL_FRAGMENT";
    public static final String MONTH_DETAIL_FRAGMENT = "MONTH_DETAIL_FRAGMENT";
    public static final String YEAR_DETAIL_FRAGMENT = "YEAR_DETAIL_FRAGMENT";

    //Detail Fragment Tags.
    public static final String DETAIL_DAILY_FRAGMENT = "DETAIL_DAILY_FRAGMENT";

    public static final String EXTRA_DATE_TAG = "EXTRA_DATE";

    //Static method to change the fragment on screen.
    public static void changeFragment(FragmentManager fm,Fragment f, String tag)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_frameLayout, f, tag);
        ft.commit();
    }

    public static void changeDetailFragment(FragmentManager fm,Fragment f, String tag)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.home_detail_fragment_container, f, tag);
        ft.commit();
    }

    public static void changeDetailActivityFragment(FragmentManager fm, Fragment f, String tag)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, f, tag);
        ft.commit();
    }
}
