package com.jcgibson.tipme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jordan on 7/21/2015.
 *
 *
 */
public class HomeDetailFragmentEmpty extends Fragment
{
    private View v;
    private FloatingActionButton mAddShiftButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_home_detail_empty, parent, false);

        //User pressed button to add shift, change to AddShiftFragment.
        mAddShiftButton = (FloatingActionButton)v.findViewById(R.id.add_shift_button);
        mAddShiftButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentCheck.changeFragment(getActivity().getSupportFragmentManager(), new AddShiftFragment(), FragmentCheck.ADD_SHIFT_TAG);
            }
        });

        return v;
    }
}

