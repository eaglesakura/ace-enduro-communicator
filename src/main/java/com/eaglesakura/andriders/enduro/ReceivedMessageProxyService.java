package com.eaglesakura.andriders.enduro;

import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.eaglesakura.android.framework.support.service.BaseService;
import com.eaglesakura.android.util.AndroidUtil;
import com.eaglesakura.util.StringUtil;

/**
 * 他端末から受け取ったRemoteIntentの内容に従って操作を分岐する
 */
public class ReceivedMessageProxyService extends BaseService {

    /**
     * 長めの表示時間
     * <p/>
     * 気づいて欲しい時
     */
    public static final long TOAST_SHOW_TIME_LONG = 1000 * 30;

    /**
     * 短めの表示時間
     * <p/>
     * 適当に通知したいとき
     */
    public static final long TOAST_SHOW_TIME_SHORT = 1000 * 10;

    /**
     * Toast表示を行わせる
     */
    public static final String ACTION_SHOW_MESSAGE = "com.eaglesakura.enduro.ACTION_SHOW_MESSAGE";


    /**
     * String / 表示するメッセージ
     */
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    /**
     * boolean / ダイアログとして表示する場合はtrue
     */
    public static final String EXTRA_BOOT_DIALOG = "EXTRA_BOOT_DIALOG";

    /**
     * boolean / ステータスバーに表示する場合はtrue
     */
    public static final String EXTRA_NOTIFICATION_STATUSBAR = "EXTRA_NOTIFICATION_STATUSBAR";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String ACTION = intent.getAction();
        if (ACTION.equals(ACTION_SHOW_MESSAGE)) {
            showMessage(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * メッセージの表示を行う
     *
     * @param intent
     */
    void showMessage(Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        if (StringUtil.isEmpty(message)) {
            // メッセージが入力されていない
            return;
        }

        AndroidUtil.playDefaultNotification(this);

        if (intent.getBooleanExtra(EXTRA_BOOT_DIALOG, false)) {
            // TODO ダイアログとして表示する
        } else {
            // Toastとして表示する

            // TODO Toastの表示をわかりやすくする
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
