package com.crodr.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Step;

public class StepActivity extends AppCompatActivity {

    public static final String ARG_STEP = "key_step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        if (savedInstanceState == null) {
            Step step = getIntent().getParcelableExtra(ARG_STEP);
            getSupportFragmentManager().beginTransaction().add(
                    R.id.container_details_step, StepFragment.newInstance(step)).commit();
        }
    }

}
