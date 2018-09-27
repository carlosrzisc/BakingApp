package com.crodr.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.crodr.bakingapp.ui.DetailsActivity;
import com.crodr.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class BakingAppUITest {
    @Rule
    public ActivityTestRule<MainActivity> mRecipeTest
            = new ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void onStartTheMain() {
//// find the view
//        onView(withId(R.id.main_toolbar_text))
//                // check matches
//                .check(ViewAssertions.matches(withText("Recipes")));
//    }

    @Test
    public void clickRecipeCard() {
        Intents.init();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // registering MainActivity's idling resource for enabling Espresso sync with MainActivity's background threads
//        Espresso.registerIdlingResources(mainActivityIdlingResource);

        onView(withId(R.id.recipes_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasExtraWithKey(DetailsActivity.ARG_RECIPE));

        Intents.release();
    }

//    @Test
//    public void onRecipeItemClicked_OpenRecipeDetails() {
//        onView(withId(R.id.recipes_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        intended(hasComponent(DetailsActivity.class.getName()));
//
//        onView(withId(R.id.recipes_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//        intended(hasComponent(DetailsActivity.class.getName()));
//
//        onView(withId(R.id.recipes_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
//        intended(hasComponent(DetailsActivity.class.getName()));
//
//        onView(withId(R.id.recipes_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
//        intended(hasComponent(DetailsActivity.class.getName()));
//    }


    @Test
    public void onLoadFinished_DisplaysRecipesRecyclerView() {
        onView(withId(R.id.recipes_list)).check(ViewAssertions.matches(isCompletelyDisplayed()));
    }

}
