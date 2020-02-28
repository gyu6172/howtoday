package com.example.howtoday.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.howtoday.Account.AccountFragment;
import com.example.howtoday.Calendar.CalendarFragment;
import com.example.howtoday.Memo.MemoFragment;
import com.example.howtoday.Weather.WeatherFragment;

import java.util.ArrayList;

public class PagerAdater extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    String titles[] =new String[]{"날씨","일정","메모","가계부"};
    public PagerAdater(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new WeatherFragment());
        fragments.add(new CalendarFragment());
        fragments.add(new MemoFragment());
        fragments.add(new AccountFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
