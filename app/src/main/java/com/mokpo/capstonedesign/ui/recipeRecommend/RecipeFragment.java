package com.mokpo.capstonedesign.ui.recipeRecommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.databinding.FragmentDashboardBinding;
import com.mokpo.capstonedesign.databinding.RecipeDashboardBinding;
import com.mokpo.capstonedesign.retrofit2.Recipe;
import com.mokpo.capstonedesign.retrofit2.RecipeDetail;
import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    private RecipeDashboardBinding binding;
    private RecipeViewModel recipeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipeViewModelFactory factory = new RecipeViewModelFactory(getContext());
        recipeViewModel =
                new ViewModelProvider(this, factory).get(RecipeViewModel.class);

        binding = RecipeDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecipeAdapter(getContext(), new ArrayList<Recipe>(), recipeViewModel));

        recipeViewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            RecipeAdapter adapter = (RecipeAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.updateData(recipes);
            }
        });

        recipeViewModel.getRecipeDetailLiveData().observe(getViewLifecycleOwner(), this::displayRecipeDetails); // 추가

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void displayRecipeDetails(RecipeDetail recipeDetail) {
        // 새로운 RecipeDetailFragment 인스턴스를 생성하고 이동
        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipeDetail);
        // 이 부분은 실제 애플리케이션의 프래그먼트 관리 방식에 따라 달라질 수 있습니다.
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}


