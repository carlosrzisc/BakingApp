package com.crodr.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;
import com.crodr.bakingapp.model.Step;

import butterknife.BindView;

public class DetailsActivity extends AppCompatActivity implements RecipeFragment.OnStepSelectedListener {

    @BindView(R.id.container_fragment_ingredients)
    FrameLayout containerIngredients;

    @BindView(R.id.container_fragment_step)
    FrameLayout containerStep;

    public static final String ARG_RECIPE = "key_recipe";

    private boolean isTabletView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (containerStep != null) {
            isTabletView = true;
            if (savedInstanceState == null) {
                showStepInFragment(null);
            }
        } else {
            isTabletView = false;
        }
        if (savedInstanceState == null) {
            Recipe recipe = getIntent().getParcelableExtra(ARG_RECIPE);
            getSupportFragmentManager().beginTransaction().add(
                    R.id.container_fragment_ingredients, RecipeFragment.newInstance(recipe), "step_fragment")
                    .commit();
        }
    }

    @Override
    public void onClick(Step step) {
        if (isTabletView) {
            showStepInFragment(step);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(StepActivity.ARG_STEP, step);
            startActivity(intent);
        }
    }

    private void showStepInFragment(Step step) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container_fragment_step, StepFragment.newInstance(step), "step_fragment")
                .commit();
    }
}
