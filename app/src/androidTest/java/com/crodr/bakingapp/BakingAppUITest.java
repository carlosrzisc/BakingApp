package com.crodr.bakingapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.crodr.bakingapp.ui.DetailsActivity;
import com.crodr.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BakingAppUITest {
    @Rule
    public ActivityTestRule<MainActivity> mRecipeTest
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeCard() {
        Intents.init();

        onView(withId(R.id.recipes_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasExtraWithKey(DetailsActivity.ARG_RECIPE));

        Intents.release();
    }

    @Test
    public void onLoadFinishedRecyclerViewDisplayed() {
        onView(withId(R.id.recipes_list)).check(ViewAssertions.matches(isCompletelyDisplayed()));
    }

}
