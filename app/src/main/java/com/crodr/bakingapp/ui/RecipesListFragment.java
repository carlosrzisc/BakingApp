package com.crodr.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;
import com.crodr.bakingapp.service.RecipesClient;
import com.crodr.bakingapp.service.RecipesService;
import com.crodr.bakingapp.ui.adapters.RecipesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesListFragment extends Fragment {
    @BindView(R.id.recipes_list)
    RecyclerView recipesList;

    RecipesAdapter adapter;

    public RecipesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipesListFragment.
     */
    public static RecipesListFragment newInstance() {
        return new RecipesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, rootView);

        recipesList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecipesAdapter(new RecipesAdapter.RecipeClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.ARG_RECIPE, recipe);
                startActivity(intent);
            }
        });
        recipesList.setAdapter(adapter);
        fetchRecipes();
        return rootView;
    }

    private void fetchRecipes() {
        RecipesService service = RecipesClient.getRetrofitInstance().create(RecipesService.class);
        Call<List<Recipe>> call = service.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                adapter.setRecipesList(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("RecipesListFragment", "ERROR ***********");
            }
        });
    }
}
