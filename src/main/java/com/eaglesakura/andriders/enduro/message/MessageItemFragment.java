package com.eaglesakura.andriders.enduro.message;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.eaglesakura.andriders.enduro.R;
import com.eaglesakura.andriders.enduro.ReceiveMessageActivity;
import com.eaglesakura.andriders.service.AcesExtensionService;
import com.eaglesakura.android.framework.support.ui.BaseFragment;
import com.eaglesakura.android.framework.support.ui.SupportAQuery;
import com.eaglesakura.android.thread.HandlerLoopController;
import com.eaglesakura.android.thread.UIHandler;
import com.eaglesakura.time.Timer;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.Time;

/**
 * 1メッセージを表示する
 */
@EFragment(R.layout.fragment_message)
public class MessageItemFragment extends BaseFragment {
    /**
     * 送信者メッセージ
     */
    String message;

    /**
     * 送信者アイコン
     */
    String senderIconUrl;

    /**
     * 送信者名
     */
    String senderName;

    /**
     * 残表示時間
     */
    int showingTimeMs;

    HandlerLoopController loopController;

    Intent intent;

    @ViewById(R.id.Message_Item_ShowLimit)
    ProgressBar showLimitBar;

    Timer showingTimer;

    /**
     * Intentから初期化する
     *
     * @param intent
     */
    public void initialize(Intent intent) {
        this.intent = intent;
    }

    @Override
    protected void onAfterViews() {
        super.onAfterViews();

        SupportAQuery q = new SupportAQuery(getView());
        q.id(R.id.Message_Item_Message).text(intent.getStringExtra(ReceiveMessageActivity.EXTRA_DIALOG_MESSAGE));
        q.id(R.id.Message_Item_Sender_Name).text(intent.getStringExtra(AcesExtensionService.EXTRA_SENDER_NAME));
        q.id(R.id.Message_Item_Sender_Icon)
                .imageUrl(intent.getStringExtra(AcesExtensionService.EXTRA_SENDER_ICON_URL), 128, 128);

        if (intent.getBooleanExtra(ReceiveMessageActivity.EXTRA_DIALOG_ENABLE, false)) {
            // ダイアログモード
            showingTimeMs = 0;
            showLimitBar.setVisibility(View.GONE);
        } else {
            // Toastモード
            showingTimeMs = 1000 * 30;
            showingTimer = new Timer();
            showLimitBar.setMax(showingTimeMs);
            showLimitBar.setProgress(showingTimeMs);
        }
    }

    /**
     * View状態を更新させる
     */
    void updateView() {
        int time = (int) showingTimer.end();
        if (time < showingTimeMs) {
            showLimitBar.setProgress(showingTimeMs - time);
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (showingTimeMs > 0) {
            loopController = new HandlerLoopController(UIHandler.getInstance()) {
                @Override
                protected void onUpdate() {
                    updateView();
                }
            };
            loopController.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loopController != null) {
            loopController.disconnect();
            loopController.dispose();
            loopController = null;
        }
    }

    @Click(R.id.Message_Item_Root)
    void clickItemRoot() {
        // クリックされたらActivityごと閉じる
        getActivity().finish();
    }
}
