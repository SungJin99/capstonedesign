package com.mokpo.capstonedesign.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.databinding.FragmentCommunityBinding;

public class CommunityFragment extends Fragment {

    private FragmentCommunityBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button regButton = view.findViewById(R.id.reg_button);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute code to navigate to NotificationsAddActivity
                Intent intent = new Intent(getActivity(), CommunityActivity.class);
                startActivity(intent);
            }
        });
    }
}




