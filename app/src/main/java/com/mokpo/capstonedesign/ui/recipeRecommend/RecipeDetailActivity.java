package com.mokpo.capstonedesign.ui.recipeRecommend;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.databinding.ActivityRecipeDetailBinding;
import com.mokpo.capstonedesign.retrofit2.RecipeDetail;
import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity {
    private ActivityRecipeDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null) {
            RecipeDetail recipeDetail = (RecipeDetail) getIntent().getSerializableExtra("recipe_detail");
            displayRecipeDetails(recipeDetail);
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
            ImageView imageView = new ImageView(this);
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
