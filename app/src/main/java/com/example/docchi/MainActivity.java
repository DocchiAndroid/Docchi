package com.example.docchi;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.example.docchi.fragments.CreatePostFragment;
import com.example.docchi.fragments.NewPostDialogFragment;
import com.example.docchi.fragments.ProfileFragment;
import com.example.docchi.fragments.SearchFragment;
import com.example.docchi.fragments.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String loggedInUser = getIntent().getStringExtra("LoggedInUser");


        Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Docchi");
            setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigation);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new TimelineFragment(loggedInUser);
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        break;
                    case R.id.action_newPoll:
                        showNewPostDialog();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        break;
                    default:
                        return true;
                }
                return true;
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

    public void setHome(){
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}