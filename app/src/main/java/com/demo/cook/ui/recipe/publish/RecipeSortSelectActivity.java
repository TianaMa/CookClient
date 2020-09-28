package com.demo.cook.ui.recipe.publish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityRecipeSortSelectBinding;
import com.demo.cook.databinding.ItemLayoutRecipeSortSelectBinding;
import com.demo.cook.ui.classes.model.data.RecipeSort;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class RecipeSortSelectActivity extends AppCompatActivity {


    ActivityRecipeSortSelectBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_recipe_sort_select);

        mBinding.tvSelectSortCancel.setOnClickListener(v -> super.onBackPressed());

        String sortId = getIntent().getStringExtra("sortId");

        try {
            List<RecipeSort> recipeSortList = new Gson().fromJson(
                    new InputStreamReader(this.getAssets().open("recipeSortMenu.json"), "UTF-8"),
                    new TypeToken<List<RecipeSort>>() {}.getType()
            );
            for (RecipeSort sort:recipeSortList){
                if(sort.getSortId().equals(sortId)){
                    sort.setSelected(true);break;
                }
            }
            mBinding.rcvClassesSortSelect.setAdapter(new CmnRcvAdapter<RecipeSort>(R.layout.item_layout_recipe_sort_select,recipeSortList) {
                @Override
                public void convert(CmnViewHolder holder, RecipeSort recipeSort, int position) {
                    ItemLayoutRecipeSortSelectBinding selectBinding = DataBindingUtil.bind(holder.itemView);
                    selectBinding.setSort(recipeSort);
                    holder.itemView.setOnClickListener(v -> {
                        for (RecipeSort sort:recipeSortList){
                            sort.setSelected(false);
                        }
                        recipeSort.setSelected(true);
                    });

                }
            });

            mBinding.tvSelectSortConfirm.setOnClickListener(v -> {
                Intent intent = new Intent();
                for (RecipeSort sort:recipeSortList){
                    if(sort.isSelected()){
                        intent.putExtra("sortId",sort.getSortId());
                        intent.putExtra("sortName",sort.getSortName());
                        break;
                    }
                }
                setResult(RESULT_OK,intent);
                finish();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}