package com.example.docchi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.docchi.adapters.FragmentAdapter;
import com.example.docchi.fragments.CreatePollFragment;
import com.example.docchi.fragments.NewPostDialogFragment;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.fragments.SearchFragment;
import com.example.docchi.fragments.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private ViewPager fragmentPager;
    private FragmentAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem = null;



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
        adapter = new FragmentAdapter(fragmentManager);
        TimelineFragment timelineFragment = new TimelineFragment(loggedInUser);
        SearchFragment searchFragment = new SearchFragment();
        CreatePollFragment createPollFragment = new CreatePollFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        adapter.addFragment(timelineFragment);
        adapter.addFragment(searchFragment);
        adapter.addFragment(createPollFragment);
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
                        fragmentPager.setCurrentItem(2);
                        showNewPostDialog();
                        break;
                    case R.id.action_profile:
                        adapter.replaceFragment(profileFragment, 3);
                        fragmentPager.setCurrentItem(3);
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

                if(position==0){
                    toolbar.setTitle("Docchi");
                }
                else if(position==1){
                    toolbar.setTitle("Search");
                }
                else if(position==2){
                    showNewPostDialog();
                    toolbar.setTitle("New Post");
                }
                else if(position==3){
                    adapter.replaceFragment(profileFragment, 3);
                    toolbar.setTitle("Docchi");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentPager.setCurrentItem(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    public FragmentAdapter getFragmentAdapter(){
        return adapter;
    }

    private void showNewPostDialog() {
        NewPostDialogFragment editNameDialogFragment = NewPostDialogFragment.newInstance("New Post");
        editNameDialogFragment.show(fragmentManager, "fragment_create_post");
    }

    public void setHome() {
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}