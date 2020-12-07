package com.example.docchi;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    ImageView ivVote;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String loggedInUser = getIntent().getStringExtra("LoggedInUser");

//        ivVote = findViewById(R.id.ivVote);
//
        //done animation
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );
//
//        ivVote.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Drawable drawable = ivVote.getDrawable();
//
//                if(drawable instanceof AnimatedVectorDrawableCompat){
//                    avd = (AnimatedVectorDrawableCompat) drawable;
//                    avd.start();
//                }else if(drawable instanceof AnimatedVectorDrawable)
//                    avd2 = (AnimatedVectorDrawable) drawable;
//                    avd2.start();
//            }
//        });


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
        FragmentManager fm = getSupportFragmentManager();
        NewPostDialogFragment editNameDialogFragment = NewPostDialogFragment.newInstance("New Post");
        editNameDialogFragment.show(fm, "fragment_create_post");
    }

    public void setHome(){
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}