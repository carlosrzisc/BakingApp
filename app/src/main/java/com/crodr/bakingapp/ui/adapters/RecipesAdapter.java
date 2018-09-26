package com.crodr.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private List<Recipe> recipesList;
    private RecipeClickListener listener;

    public RecipesAdapter(RecipeClickListener listener) {
        this.listener = listener;
        recipesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipesList.get(position), listener);
    }

    @Override
    public int getItemCount() { return recipesList.size(); }


    public void setRecipesList(List<Recipe> recipes) {
        this.recipesList = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_recipe_name)
        TextView txtRecipe;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Recipe recipe, final RecipeClickListener listener) {
            txtRecipe.setText(recipe.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(recipe);
                }
            });
        }
    }

    public interface RecipeClickListener {
        void onClick(Recipe recipe);
    }
}
