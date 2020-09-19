package com.demo.cook.ui.publish.type;

import com.demo.baselib.design.BaseViewModel;

import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.user.model.data.User;

public class PublishTypeViewModel extends BaseViewModel {
    public User user = Storage.getUser();
}
