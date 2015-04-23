package com.eaglesakura.andriders.enduro;

import android.os.Bundle;

import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.central.TeamProtocolReceiver;
import com.eaglesakura.andriders.central.event.TeamDataHandler;
import com.eaglesakura.andriders.protocol.TeamProtocol;
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

    private TeamDataHandler teamDataHandler = new TeamDataHandler() {
        /**
         *
         * @param receiver             受信したレシーバ
         * @param master               マスターデータ
         * @param memberId             参加したメンバーのID
         * @param memberMasterReceiver 受信したチームレシーバ
         */
        @Override
        public void onJoinTeamMember(TeamProtocolReceiver receiver, TeamProtocol.TeamPayload master, String memberId, TeamMemberReceiver memberMasterReceiver) {
        }
    };
}
