package com.mobile.adapter;

import com.mobile.poolup.CreateFragment;
import com.mobile.poolup.ListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
		switch(index) {
		case 0: 
			return new CreateFragment(); 
		case 1: 
			return new ListFragment(); 
		}
		return null; 
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
}
