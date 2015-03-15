package com.acadgild.tabsassignment;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.os.Bundle;

import com.acadgild.tabsassignment.adapter.TabsPagerAdapter;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TabsPagerAdapter adapter;
    private ActionBar actionBar;

    private int[] tabs = {android.R.drawable.ic_media_previous, android.R.drawable.ic_media_play, android.R.drawable.ic_media_next };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        adapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int tab : tabs) {
            actionBar.addTab(actionBar.newTab().setIcon(tab).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        actionBar.setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }




}
