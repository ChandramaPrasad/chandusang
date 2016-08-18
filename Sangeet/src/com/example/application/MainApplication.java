package com.example.application;

import android.app.Application;
import android.content.res.Configuration;

import com.example.application.controller.ApplicationController;

public class MainApplication extends Application {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ApplicationController.getInstance().setContext(getApplicationContext());
		ApplicationController.getInstance().initialize();
		ApplicationController.getInstance().setApplication(this);

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}
}
