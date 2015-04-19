package com.eaglesakura.andriders.enduro;

import com.eaglesakura.andriders.service.AcesExtensionService;
import com.eaglesakura.util.LogUtil;

/**
 * ACE Background Service
 */
public class EnduroExtensionService extends AcesExtensionService {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log("onCreate(%s)", getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log("onDestroy(%s)", getClass().getSimpleName());
    }
}
