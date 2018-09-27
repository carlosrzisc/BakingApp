package com.crodr.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;
import com.crodr.bakingapp.ui.RecipeFragment;
import com.google.gson.Gson;

public class RecipeRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(getApplicationContext(), intent);
    }

    class RecipeRemoteViewsFactory implements RemoteViewsFactory {
        Context context;
        Intent intent;
        Recipe recipe = new Recipe();

        RecipeRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preference_baking_key), Context.MODE_PRIVATE);

            String jsonRecipe = prefs.getString(RecipeFragment.JSON_RECIPE, "");
            Log.i("WIDGETTT",  jsonRecipe);
            if (!jsonRecipe.isEmpty()) {
                recipe = new Gson().fromJson(jsonRecipe, Recipe.class);
            }
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            Log.i("WIDGETTT",  recipe.getIngredients().get(i).getIngredient());
            views.setTextViewText(R.id.txt_widget_item, recipe.getIngredients().get(i).getIngredient());
            return views;
        }

        @Override
        public int getCount() { return recipe.getIngredients().size(); }

        @Override
        public RemoteViews getLoadingView() { return null; }

        @Override
        public int getViewTypeCount() { return 1; }

        @Override
        public long getItemId(int i) { return 0; }

        @Override
        public boolean hasStableIds() { return true; }

        @Override
        public void onCreate() { }

        @Override
        public void onDestroy() { }
    }

}
