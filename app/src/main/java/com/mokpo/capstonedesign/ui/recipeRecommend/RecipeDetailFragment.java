package com.mokpo.capstonedesign.ui.recipeRecommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.databinding.FragmentDashboardBinding;
import com.mokpo.capstonedesign.databinding.FragmentRecipeDetailBinding;
import com.mokpo.capstonedesign.retrofit2.Recipe;
import com.mokpo.capstonedesign.retrofit2.RecipeDetail;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class RecipeDetailFragment extends Fragment {

    private FragmentRecipeDetailBinding binding;

    public RecipeDetailFragment() {
        /* Required empty public constructor */
    }

    public static RecipeDetailFragment newInstance(RecipeDetail recipeDetail) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeDetail", recipeDetail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            RecipeDetail recipeDetail = (RecipeDetail) getArguments().getSerializable("recipeDetail");
            displayRecipeDetails(recipeDetail);
        }

        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 이 부분을 추가하세요.
        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void displayRecipeDetails(RecipeDetail recipeDetail) {
        ImageView ivRecipeImage = binding.ivRecipeImage;
        TextView tvRcpNm = binding.tvRcpNm;
        TextView tvManualR = binding.tvManualR;
        String manualText = recipeDetail.getManual_R().replace(".", ".\n");
        tvManualR.setText(manualText);
        String manualImgText = recipeDetail.getManual_IMG_R();
        LinearLayout layoutImages = binding.layoutImages;

        // 이미지 URL이 \n으로 분리되어 있으므로 \n을 기준으로 분리
        String[] imageUrls = manualImgText.split("\n");

        // 이미지 URL을 돌면서 ImageView를 생성하고 Picasso로 이미지 로드
        for (String imageUrl : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(imageUrl).into(imageView);

            // ImageView를 LinearLayout에 추가
            layoutImages.addView(imageView);
        }
        TextView tvRcpPartsDtls = binding.tvRcpPartsDtls;
        TextView tvRcpWay2 = binding.tvRcpWay2;
        TextView tvInfoNa = binding.tvInfoNa;
        TextView tvInfoPro = binding.tvInfoPro;
        TextView tvInfoFat = binding.tvInfoFat;
        TextView tvHashTag = binding.tvHashTag;
        TextView tvRcpPat2 = binding.tvRcpPat2;
        TextView tvInfoCar = binding.tvInfoCar;
        TextView tvRcpNaTip = binding.tvRcpNaTip;
        TextView tvInfoEng = binding.tvInfoEng;

        tvRcpNm.setText(recipeDetail.getRCP_NM());
        tvManualR.setText(recipeDetail.getManual_R());
        tvRcpPartsDtls.setText(recipeDetail.getRCP_PARTS_DTLS());
        tvRcpWay2.setText(recipeDetail.getRCP_WAY2());
        tvInfoNa.setText(recipeDetail.getINFO_NA());
        tvInfoPro.setText(recipeDetail.getINFO_PRO());
        tvInfoFat.setText(recipeDetail.getINFO_FAT());
        tvHashTag.setText(recipeDetail.getHASH_TAG());
        tvRcpPat2.setText(recipeDetail.getRCP_PAT2());
        tvInfoCar.setText(recipeDetail.getINFO_CAR());
        tvRcpNaTip.setText(recipeDetail.getRCP_NA_TIP());
        tvInfoEng.setText(recipeDetail.getINFO_ENG());

        if (recipeDetail.getATT_FILE_NO_MAIN() != null && !recipeDetail.getATT_FILE_NO_MAIN().isEmpty()) {
            Picasso.get().load(recipeDetail.getATT_FILE_NO_MAIN()).into(ivRecipeImage);
        }
    }
}

