package com.mokpo.capstonedesign.ui.community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mokpo.capstonedesign.databinding.FragmentCommunityBinding;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment {
    private static final int REQUEST_COMMENT_UPDATE = 1;
    private RecyclerView recyclerView;
    private FragmentCommunityBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.communtRecyclerview;
        root.findViewById(R.id.reg_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IngredientAddActivity로 이동하는 코드
                Intent intent = new Intent(getActivity(), PostAddActivity.class);
                startActivity(intent);
            }
        });

        fetchPostList();

        return root;
    }


    private void fetchPostList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accessToken = sharedPreferences.getString("jwt_token", "");
        System.out.println(accessToken);
        ApiService foodApi = ApiClient.getApiService();
        Call<ArrayList<Post>> call = foodApi.getPostList("Bearer " + accessToken);
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Post> postList = response.body();
                    displayPostList(postList);
                } else {
                    // Error handling
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                // Error handling
            }
        });
    }

    private void displayPostList(ArrayList<Post> postList) {
        RecyclerView.Adapter adapter = new PostAdapter(requireActivity(), postList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        fetchPostList();
    }
}