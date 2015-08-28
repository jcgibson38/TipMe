package com.jcgibson.tipme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* The launcher Activity, which hosts the HomeFragment.
*
* */

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnDateSelectedListener, HomeDetailFragmentFull.OnDeleteListener
{
    private Toolbar mToolbar;

    //Static variables.
    private static Date sCurrentDate;
    private static Shift sCurrentShift;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sCurrentDate = new Date();
        sCurrentShift = new Shift();

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Inflate the HomeFragment.
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container_frameLayout);
        if(f == null)
        {
            f = new HomeFragment();
            fm.beginTransaction().add(R.id.fragment_container_frameLayout, f, FragmentCheck.HOME_FRAGMENT_CALENDAR).commit();
        }
    }

    /*
    * Getters
    * */

    public static Date getDate()
    {
        return sCurrentDate;
    }

    public static Shift getShift()
    {
        return sCurrentShift;
    }

    /*
    * Setters
    * */

    /*
    * Interface
    * */

    @Override
    public void onShiftFound(Date date, Shift shift)
    {
        sCurrentDate = date;
        sCurrentShift = shift;
    }

    @Override
    public void onDateSelected(Date date)
    {
        sCurrentDate = date;
    }

    @Override
    public void onShiftDeleted(Shift shift)
    {
        FragmentManager fm = getSupportFragmentManager();
        HomeFragment f = (HomeFragment)fm.findFragmentByTag(FragmentCheck.HOME_FRAGMENT_CALENDAR);
        f.deleteDecorations();
    }

    @Override
    public void onRedecorateListener()
    {
        FragmentManager fm = getSupportFragmentManager();
        HomeFragment f = (HomeFragment)fm.findFragmentByTag(FragmentCheck.HOME_FRAGMENT_CALENDAR);
        f.decorate();
    }

    /*
    * Other methods
    * */

    public static String getDateAsString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(sCurrentDate);
    }
}
