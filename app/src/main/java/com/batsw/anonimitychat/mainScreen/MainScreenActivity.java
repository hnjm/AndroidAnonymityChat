package com.batsw.anonimitychat.mainScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.batsw.anonimitychat.R;
import com.batsw.anonimitychat.mainScreen.tabs.TabChats;
import com.batsw.anonimitychat.mainScreen.tabs.TabContats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudor on 3/27/2017.
 */

public class MainScreenActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    private ViewPager mViewPager;
    //main menu tabbed
    private TabHost mTabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);

        init();

        initTabs();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.main_screen_view_pager);

        mTabHost = (TabHost) findViewById(R.id.main_screen_tab_host);
        mTabHost.setup();

        List<Fragment> listFragments = new ArrayList<>();
        listFragments.add(new TabContats());
        listFragments.add(new TabChats());

        MainScreenAdapter mainScreenAdapter = new MainScreenAdapter(getSupportFragmentManager(), listFragments);
        mViewPager.setAdapter(mainScreenAdapter);

        mViewPager.setOnPageChangeListener(this);
    }

    private void initTabs() {

        String[] tabNames = {"Contacts", "Chat List"};

        for (int i = 0; i < tabNames.length; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = mTabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(getApplicationContext()));
            mTabHost.addTab(tabSpec);
        }

        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_screen_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int selectedPage) {
        mTabHost.setCurrentTab(selectedPage);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int selectedPage = mTabHost.getCurrentTab();
        mViewPager.setCurrentItem(selectedPage);

        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.main_screen_scroll_view);
        View tabView = mTabHost.getCurrentTabView();
        int scrollPosition = tabView.getLeft() - (horizontalScrollView.getWidth() - tabView.getWidth()) / 2;
        horizontalScrollView.smoothScrollBy(scrollPosition, 0);
    }

    private class FakeContent implements TabHost.TabContentFactory {
        Context context;

        public FakeContent(Context context) {
            this.context = context;
        }

        @Override
        public View createTabContent(String s) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }
}