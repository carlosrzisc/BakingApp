package com.crodr.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;
import com.crodr.bakingapp.ui.RecipeFragment;
import com.google.gson.Gson;

public class RecipeWidgetProvider extends AppWidgetProvider {
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preference_baking_key), Context.MODE_PRIVATE);
        String jsonIngredients = prefs.getString(RecipeFragment.JSON_RECIPE, "");

        if (!jsonIngredients.isEmpty()) {
            Recipe recipe = new Gson().fromJson(jsonIngredients, Recipe.class);

            for (int appWidgetId : appWidgetIds) {
                Intent intent = new Intent(context, RecipeRemoteViewsService.class);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

                /////////
                /////////
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
                views.setTextViewText(R.id.appwidget_text, recipe.getName());

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
                /////////
                /////////


                ///////////
//                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
//                remoteViews.setRemoteAdapter(R.id.widget_list_view);
                ///////////



                appWidgetManager.updateAppWidget(appWidgetId, views);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_ingredients);
            }
            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName recipeWidget = new ComponentName(context.getApplicationContext(), RecipeWidgetProvider.class);
        onUpdate(context, widgetManager, widgetManager.getAppWidgetIds(recipeWidget));
    }
}
