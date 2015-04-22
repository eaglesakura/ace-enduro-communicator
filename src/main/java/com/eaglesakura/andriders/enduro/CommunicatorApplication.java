package com.eaglesakura.andriders.enduro;

import android.app.Application;

import com.eaglesakura.android.framework.FrameworkCentral;
import com.eaglesakura.android.net.NetworkConnector;

/**
 * Main app
 */
public class CommunicatorApplication extends Application implements FrameworkCentral.FrameworkApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        FrameworkCentral.onApplicationCreate(this);
        FrameworkCentral.requestDeploygateInstall();

        NetworkConnector.initializeDefaultConnector(this);
    }

    @Override
    public void onApplicationUpdated(int oldVersionCode, int newVersionCode, String oldVersionName, String newVersionName) {

    }
}
