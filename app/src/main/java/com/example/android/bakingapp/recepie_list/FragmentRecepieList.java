package com.example.android.bakingapp.recepie_list;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bakingapp.Beans.Recipe;
import com.example.android.bakingapp.BuildConfig;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeAdapter;
import com.example.android.bakingapp.custom.StatefulRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 16/07/17.
 */

public class FragmentRecepieList extends Fragment implements RecipesListContract.View, RecipeAdapter.ItemListener {

    @BindView(R.id.pb)
    ProgressBar mPb;
    @BindView(R.id.rv_fragment_recipes)
    StatefulRecyclerView mRvFragmentRecipes;
    Unbinder unbinder;
    private RecipesListContract.Presenter recipeListPresenter;
    private RecepiePresenter mRecepiePresenter;
    private RecipeAdapter mAdapter;
    private static String RECIPE_SAVED_INSATANCE_KEY = "recipesList";
    private List<Recipe> mRecipeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecepiePresenter = new RecepiePresenter(this);
        mRecipeList = new ArrayList<>();
        if (null != savedInstanceState) {

            Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
            mRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_SAVED_INSATANCE_KEY);


        } else {
            Toast.makeText(getContext(), "not saved", Toast.LENGTH_SHORT).show();
            mRecipeList.clear();
            mRecepiePresenter.fetchRecipesFromServre();
        }

        mAdapter = new RecipeAdapter(mRecipeList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRvFragmentRecipes.setLayoutManager(mLayoutManager);
        mRvFragmentRecipes.setItemAnimator(new DefaultItemAnimator());
        mRvFragmentRecipes.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_SAVED_INSATANCE_KEY, (ArrayList<? extends Parcelable>) mRecipeList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        mRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_SAVED_INSATANCE_KEY);
    }


    @Override
    public void setPresenter(RecipesListContract.Presenter presenter) {
        this.recipeListPresenter = presenter;
    }

    @Override
    public void showRecipesList(List<Recipe> recipeList) {


        mRecipeList = recipeList;
        if (BuildConfig.DEBUG) {
            Toast.makeText(getContext(), "Size of data interface" + mRecipeList.size(), Toast.LENGTH_SHORT).show();
        }
        mAdapter = new RecipeAdapter(mRecipeList, this);
        mRvFragmentRecipes.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgressBar() {
        mPb.setVisibility(View.VISIBLE);


    }

    @Override
    public void hidProgressBar() {
        mPb.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showRecipeDetailsUI(int recipeId) {

    }

    @Override
    public void onItemClick(Recipe item) {

        if (BuildConfig.DEBUG) {
            Toast.makeText(getContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
        }

    }
}
