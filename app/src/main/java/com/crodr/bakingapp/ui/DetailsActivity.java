package com.crodr.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crodr.bakingapp.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String ARG_RECIPE = "key_recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
