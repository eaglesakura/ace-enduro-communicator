package com.eaglesakura.andriders.enduro;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.eaglesakura.andriders.enduro.message.MessageItemFragment;
import com.eaglesakura.android.framework.support.ui.BaseActivity;
import com.eaglesakura.android.framework.support.ui.SupportAnnotationUtil;
import com.eaglesakura.material.widget.MaterialAlertDialog;
import com.eaglesakura.util.StringUtil;

import org.androidannotations.annotations.EActivity;

/**
 * 受信したメッセージの表示を行うActivity
 * <p/>
 * Intentに従い、「一定時間で消える(Toast)」 or 「ずっと居座る(Dialog)」のどちらかで動作する
 * <p/>
 * Toastモードの場合、画面タップでも消えるようにしておく。
 */
@EActivity
public class ReceiveMessageActivity extends BaseActivity {
    /**
     * Toast表示を行わせる
     */
    public static final String ACTION_SHOW_MESSAGE = "com.eaglesakura.enduro.ACTION_SHOW_MESSAGE";

    /**
     * ダイアログの表示メッセージ
     */
    public static final String EXTRA_DIALOG_MESSAGE = "EXTRA_DIALOG_MESSAGE";

    /**
     * ダイアログの表示時間
     */
    public static final String EXTRA_DIALOG_LIMIT_MS = "EXTRA_DIALOG_LIMIT_MS";

    /**
     * ダイアログモードとして起動するならtrue
     */
    public static final String EXTRA_DIALOG_ENABLE = "EXTRA_DIALOG_ENABLE";

    /**
     * ステータスバーに通知を残す場合はtrue
     */
    public static final String EXTRA_STATUSBAR_ENABLE = "EXTRA_STATUSBAR_ENABLE";

    /**
     * ユーザーの表示名
     */
    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";

    /**
     * ユーザー表示URL
     */
    public static final String EXTRA_USER_ICON_URL = "EXTRA_USER_ICON_URL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            {
                MessageItemFragment fragment = SupportAnnotationUtil.newFragment(MessageItemFragment.class);
                fragment.initialize(getIntent());
                transaction.add(R.id.Message_Main_Container, fragment);
            }
            transaction.commit();
        }
//        String message = getIntent().getStringExtra(EXTRA_DIALOG_MESSAGE);
//        if (StringUtil.isEmpty(message)) {
//            message = "メッセージが設定されていません";
//        }
//
//        MaterialAlertDialog dialog = new MaterialAlertDialog(this);
//        dialog.setTitle("チームメッセージ");
//        dialog.setMessage(message);
//        dialog.setPositiveButton("閉じる", null);
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                finish();
//            }
//        });
//        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // pause時に強制的にActivityを閉じる
        if (!isFinishing()) {
            finish();
        }
    }
}
