package com.demo.cook.ui.recipe.model.data;

import androidx.databinding.BaseObservable;

public class RecipeStep extends BaseObservable {

    private String stepContent;

    private String stepImg;

    private int orderIndex;

    public String getStepContent() {
        return stepContent;
    }

    public void setStepContent(String stepContent) {
        this.stepContent = stepContent == null ? null : stepContent.trim();
    }

    public String getStepImg() {
        return stepImg;
    }

    public void setStepImg(String stepImg) {
        this.stepImg = stepImg == null ? null : stepImg.trim();
        notifyChange();
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}