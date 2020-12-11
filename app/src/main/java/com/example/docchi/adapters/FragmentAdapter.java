package com.example.docchi.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.docchi.fragments.CreatePollFragment;
import com.example.docchi.fragments.CreatePostFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> frags = new ArrayList<>();

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment f){
        frags.add(f);
    }

    public void replaceFragment(Fragment f, int position){
        frags.set(position, f);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(object instanceof CreatePostFragment || object instanceof CreatePollFragment){
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return frags.size();
    }
}
