package com.eaglesakura.andriders.enduro;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.eaglesakura.andriders.central.TeamMemberReceiver;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.andriders.service.AcesExtensionService;
import com.eaglesakura.android.annotations.AnnotationUtil;
import com.eaglesakura.android.util.AndroidUtil;
import com.eaglesakura.android.util.ContextUtil;
import com.eaglesakura.util.LogUtil;

import org.androidannotations.annotations.EService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ACE Background Service
 */
@EService
public class EnduroExtensionService extends AcesExtensionService {

    /**
     * 全メンバーにメッセージを送る
     */
    public static final String ACTION_SEND_INTENT_TO_ALLMEMBERS = "com.eaglesakura.enduro.ACTION_SEND_INTENT_TO_ALLMEMBERS";

    /**
     * 指定したメンバーにメッセージを送る
     */
    public static final String ACTION_SEND_INTENT_ONE_MEMBER = "com.eaglesakura.enduro.ACTION_SEND_INTENT_ONE_MEMBER";


    /**
     * Toast表示を行わせる
     */
    public static final String ACTION_SHOW_TOAST = "com.eaglesakura.enduro.ACTION_SHOW_TOAST";


    /**
     * 表示するメッセージ
     */
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    /**
     * Toast起動を行う。
     * <p/>
     * ユーザーが操作しなくても画面から消える
     */
    public static final int BOOT_MODE_TOAST = 2;

    /**
     * メンバーIDを示す
     */
    public static final String EXTRA_MEMBER_ID = "EXTRA_MEMBER_ID";

    /**
     * 送信対象のIntent
     */
    public static final String EXTRA_REMOTE_INTENT = "EXTRA_REMOTE_INTENT";


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log("onCreate(%s)", getClass().getSimpleName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String ACTION = intent.getAction();
        if (ACTION.equals(ACTION_SEND_INTENT_TO_ALLMEMBERS)) {
            // 全メンバーに送信する
            List<TeamMemberReceiver> members = getTeamProtocolReceiver().listMembers();
            if (!members.isEmpty()) {
                List<String> userIdList = new ArrayList<>();
                for (TeamMemberReceiver receiver : members) {
                    userIdList.add(receiver.getLastReceivedMemberData().getUserId());
                }

                sendIntentToMembers(userIdList, intent);
            }
        } else if (ACTION.equals(ACTION_SEND_INTENT_ONE_MEMBER)) {
            // 指定したメンバーにのみ送る
            String userId = intent.getStringExtra(EXTRA_MEMBER_ID);
            if (getTeamProtocolReceiver().getMemberReceiver(userId) != null) {
                // 送信対象が接続されている
                sendIntentToMembers(Arrays.asList(userId), intent);
            }
        } else if (ACTION.equals(ACTION_SHOW_TOAST)) {
            // 別端末から送られてきたメッセージに従い、Toastを表示する
            showToast(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 指定したメンバー一覧にIntentを送信する
     *
     * @param userIdList
     * @param intent
     */
    void sendIntentToMembers(List<String> userIdList, Intent intent) {
        try {
            CommandProtocol.IntentPayload intentPayload = CommandProtocol.IntentPayload.parseFrom(intent.getByteArrayExtra(EXTRA_REMOTE_INTENT));

            List<TeamMemberReceiver> members = getTeamProtocolReceiver().listMembers();
            for (TeamMemberReceiver member : members) {
                if (userIdList.contains(member.getLastReceivedMemberData().getUserId())) {
                    // 送信対象
                    LogUtil.log("send message user(%s)", member.getLastReceivedMemberData().getUserId());

                    sendRemoteIntent(member, intentPayload);
                }
            }
        } catch (Exception e) {
            LogUtil.log(e);
        }
    }

    /**
     * 指定された内容のToastを表示する
     *
     * @param intent
     */
    void showToast(Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        AndroidUtil.playDefaultNotification(this);

        // TODO Toastの表示をわかりやすくする
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log("onDestroy(%s)", getClass().getSimpleName());
    }

    public static boolean isRunning(Context context) {
        return ContextUtil.isServiceRunning(context, AnnotationUtil.annotation(EnduroExtensionService.class));
    }
}
