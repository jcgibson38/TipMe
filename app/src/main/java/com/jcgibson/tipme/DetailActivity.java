package com.jcgibson.tipme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Jordan on 8/19/2015.
 */
public class DetailActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private Spinner mSpinner;
    private Shift mCurrentShift;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCurrentShift = getIntent().getParcelableExtra(HomeDetailFragmentFull.DAILY_DETAIL_EXTRA);

        //Set toolbar.
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Configure the spinner.
        mSpinner = (Spinner)findViewById(R.id.timePeriodSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.timePeriodsArray, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            //Changed to the needed detail fragment.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                FragmentManager fm = getSupportFragmentManager();

                switch(position)
                {
                    case 0:
                        Log.d("LOG", "1");
                        FragmentCheck.changeDetailActivityFragment(fm, new DailyDetailFragment(), FragmentCheck.DAY_DETAIL_FRAGMENT);
                        break;
                    case 1:
                        Log.d("LOG", "2");
                        FragmentCheck.changeDetailActivityFragment(fm, new WeeklyDetailFragment(), FragmentCheck.WEEK_DETAIL_FRAGMENT);
                        break;
                    default:
                        Log.d("LOG", "d");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //Inflate fragment.
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragmentContainer);
        if(f == null)
        {
            f = new DailyDetailFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, f, FragmentCheck.DETAIL_DAILY_FRAGMENT).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(DetailActivity.this) != null)
                {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Shift getCurrentShift()
    {
        return mCurrentShift;
    }
}
