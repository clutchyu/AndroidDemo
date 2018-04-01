package com.clutch.student.Adapter;

/**
 * Created by clutchyu on 2018/3/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.clutch.student.Fragment.FirstFragment;
import com.clutch.student.Fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;




public class ViewPagerAdapter extends FragmentPagerAdapter {
    private  List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentManager fm;
    public int getItemPosition(Object object) {

        if (object instanceof FirstFragment) {
            ((FirstFragment)object).update();
        }else if(object instanceof SecondFragment){
            ((SecondFragment)object).updata();
        }
        return super.getItemPosition(object);
    }

    public void setFragments(List<Fragment> fragments) {
        if (this.mFragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mFragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.mFragmentList = fragments;
        notifyDataSetChanged();
    }
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        this.fm = manager;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }



}
