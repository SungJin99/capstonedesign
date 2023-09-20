package com.mokpo.capstonedesign.ui.IngredientManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mokpo.capstonedesign.SwipeController;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.IngredientAddActivity;
import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.databinding.FragmentIngredientManagementBinding;
import com.mokpo.capstonedesign.retrofit2.IngredientResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private FragmentIngredientManagementBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentIngredientManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        root.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IngredientAddActivity로 이동하는 코드
                Intent intent = new Intent(getActivity(), IngredientAddActivity.class);
                startActivity(intent);
            }
        });
        fetchFoodList();
        SwipeController swipeController = new SwipeController(recyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void fetchFoodList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accessToken = sharedPreferences.getString("jwt_token", "");
        System.out.println(accessToken);
        ApiService foodApi = ApiClient.getApiService();
        Call<List<IngredientResponse>> call = foodApi.getFoodList("Bearer " + accessToken);
        call.enqueue(new Callback<List<IngredientResponse>>() {
            @Override
            public void onResponse(Call<List<IngredientResponse>> call, Response<List<IngredientResponse>> response) {
                if (response.isSuccessful()) {
                    List<IngredientResponse> foodList = response.body();
                    displayFoodList(foodList);
                } else {
                    // Error handling
                }
            }

            @Override
            public void onFailure(Call<List<IngredientResponse>> call, Throwable t) {
                // Error handling
            }
        });
    }

    private void displayFoodList(List<IngredientResponse> foodList) {
        RecyclerView.Adapter adapter = new IngredientAdapter(foodList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}