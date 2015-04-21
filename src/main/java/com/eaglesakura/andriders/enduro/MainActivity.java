package com.eaglesakura.andriders.enduro;

import android.os.Bundle;

import com.eaglesakura.andriders.central.TeamProtocolReceiver;
import com.eaglesakura.android.framework.support.ui.BaseActivity;

import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    TeamProtocolReceiver teamProtocolReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamProtocolReceiver = new TeamProtocolReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        teamProtocolReceiver.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        teamProtocolReceiver.disconnect();
    }
}
