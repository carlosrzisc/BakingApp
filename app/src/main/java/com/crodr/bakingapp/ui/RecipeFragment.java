package com.crodr.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Recipe;
import com.crodr.bakingapp.model.Step;
import com.crodr.bakingapp.ui.adapters.IngredientsAdapter;
import com.crodr.bakingapp.ui.adapters.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnStepSelectedListener} interface
 * to handle interaction events.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    private static final String ARG_PARAM_RECIPE = "param_step";
    private Recipe paramRecipe;
    private OnStepSelectedListener mListener;

    @BindView(R.id.list_ingredients)
    RecyclerView ingredients;

    @BindView(R.id.list_steps)
    RecyclerView steps;

    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe Parameter Recipe.
     * @return A new instance of fragment RecipeFragment.
     */
    public static RecipeFragment newInstance(Recipe recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramRecipe = getArguments().getParcelable(ARG_PARAM_RECIPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);
        ingredients.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ingredients.setHasFixedSize(true);
        ingredients.setAdapter(new IngredientsAdapter(paramRecipe.getIngredients()));

        steps.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        steps.setHasFixedSize(true);
        steps.setAdapter(new StepsAdapter(paramRecipe.getSteps(), new StepsAdapter.StepClickListener() {
            @Override
            public void onClick(Step step) {
                mListener.onClick(step);
            }
        }));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepSelectedListener) {
            mListener = (OnStepSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnStepSelectedListener {
        void onClick(Step step);
    }
}
