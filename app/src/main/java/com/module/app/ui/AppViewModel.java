package com.module.app.ui;

import androidx.lifecycle.ViewModel;

import com.module.app.request.AppRequest;

/**
 * @author BD
 */
public class AppViewModel extends ViewModel {

    public final AppRequest appRequest = new AppRequest();
}
