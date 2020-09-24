package com.demo.cook.base.http;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.cook.R;
import com.demo.cook.utils.view.DialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<D> implements Callback<RtnResult<D>> {

    @Override
    public void onResponse(Call<RtnResult<D>> call, Response<RtnResult<D>> response) {
        finallyCall();

        if(response.code()==200){
            RtnResult<D> result=response.body();
            if(0==result.getCode()){
                onSuccess(result.getData());
            }else {
                ToastyUtils.showError(result.getMsg());
            }
        }else {
            ToastyUtils.showError(R.string.text_service_error);
        }
    }

    @Override
    public void onFailure(Call<RtnResult<D>> call, Throwable t) {
        t.printStackTrace();
        finallyCall();
        ToastyUtils.showError(R.string.text_service_error);
    }

    public abstract void onSuccess(D data);

    public void finallyCall(){}
}
