package com.crodr.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientList;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsAdapter.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemCount() { return (ingredientList != null)? ingredientList.size(): 0; }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_ingredient)
        TextView txtIngredient;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Ingredient ingredient) {
            String ingredientDetails = "[ " + ingredient.getQuantity() + ingredient.getMeasure() + " ] " + ingredient.getIngredient();
            txtIngredient.setText(ingredientDetails);
        }
    }
}
