package com.demo.baselib.design;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.baselib.base.BaseContext;

public class BaseViewModel extends ViewModel {

    public UIChangeObservable uiChangeObservable =new UIChangeObservable();

    class UIChangeObservable{
        MutableLiveData<String> showLoading= new MutableLiveData<>();
    }

    protected void showLoading(String msg){
        uiChangeObservable.showLoading.postValue(msg);
    }

    protected void showLoading(@StringRes int msgId){
        uiChangeObservable.showLoading.postValue(BaseContext.getInstance().getString(msgId));
    }
    protected void closeLoading(){
        uiChangeObservable.showLoading.postValue(null);
    }

}
