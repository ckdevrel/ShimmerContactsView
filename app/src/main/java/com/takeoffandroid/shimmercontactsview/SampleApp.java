package com.takeoffandroid.shimmercontactsview;

import android.app.Application;

import com.takeoffandroid.shimmercontactsview.views.typefacehelper.TypefaceHelper;

public class SampleApp extends Application {
    @Override
	public void onCreate() {
		super.onCreate();
		TypefaceHelper.initialize(this);
	}

	@Override
	public void onTerminate() {
		TypefaceHelper.destroy();
		super.onTerminate();
	}
}