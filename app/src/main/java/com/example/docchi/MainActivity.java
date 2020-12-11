package com.example.docchi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.docchi.adapters.FragmentAdapter;
import com.example.docchi.fragments.NewPostDialogFragment;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.fragments.SearchFragment;
import com.example.docchi.fragments.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    ViewPager fragmentPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String loggedInUser = getIntent().getStringExtra("LoggedInUser");


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Docchi");
        setSupportActionBar(toolbar);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        fragmentPager = findViewById(R.id.fragmentPager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        TimelineFragment timelineFragment = new TimelineFragment();
        SearchFragment searchFragment = new SearchFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        adapter.addFragment(timelineFragment);
        adapter.addFragment(searchFragment);
        adapter.addFragment(profileFragment);
        fragmentPager.setAdapter(adapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragmentPager.setCurrentItem(0);
                        break;
                    case R.id.action_search:
                        fragmentPager.setCurrentItem(1);
                        break;
                    case R.id.action_newPoll:
                        showNewPostDialog();
                        break;
                    case R.id.action_profile:
                        fragmentPager.setCurrentItem(2);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        fragmentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem != null){
                    prevMenuItem.setChecked(false);
                }
                else{
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    private void showNewPostDialog() {
        NewPostDialogFragment editNameDialogFragment = NewPostDialogFragment.newInstance("New Post");
        editNameDialogFragment.show(fragmentManager, "fragment_create_post");
    }

    public void setHome() {
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}