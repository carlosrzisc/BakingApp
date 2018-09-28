package com.crodr.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;
import com.crodr.bakingapp.ui.RecipeFragment;
import com.google.gson.Gson;

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences prefs = context.getSharedPreferences(RecipeFragment.PREF_RECIPE, Context.MODE_PRIVATE);
        String jsonRecipe = prefs.getString(RecipeFragment.JSON_RECIPE, "");

        if (!jsonRecipe.isEmpty()) {
            Recipe recipe = new Gson().fromJson(jsonRecipe, Recipe.class);

            for (int appWidgetId : appWidgetIds) {
                RemoteViews views = new RemoteViews(
                        context.getPackageName(),
                        R.layout.widget_recipe
                );
                Intent intent = new Intent(context, RecipeRemoteViewsService.class);
                views.setRemoteAdapter(R.id.recipe_ingredients, intent);
                views.setTextViewText(R.id.appwidget_text, recipe.getName());

                appWidgetManager.updateAppWidget(appWidgetId, views);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_ingredients);
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName recipeWidget = new ComponentName(context.getApplicationContext(), RecipeWidgetProvider.class);
        this.onUpdate(context, appWidgetManager, widgetManager.getAppWidgetIds(recipeWidget));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName recipeWidget = new ComponentName(context.getApplicationContext(), RecipeWidgetProvider.class);
        this.onUpdate(context, widgetManager, widgetManager.getAppWidgetIds(recipeWidget));
    }
}
