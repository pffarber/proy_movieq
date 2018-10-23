package com.example.view;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recyclerviewbase.R;

public class YouTubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();
        BlankFragment blankFragment = new BlankFragment();
        blankFragment.setArguments(unBundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container_fragment, blankFragment);
        transaction.commit();


    }

}
