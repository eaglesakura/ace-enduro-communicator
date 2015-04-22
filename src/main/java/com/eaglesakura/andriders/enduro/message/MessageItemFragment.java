package com.eaglesakura.andriders.enduro.message;

import android.content.Intent;

import com.eaglesakura.andriders.enduro.R;
import com.eaglesakura.andriders.enduro.ReceiveMessageActivity;
import com.eaglesakura.andriders.service.AcesExtensionService;
import com.eaglesakura.android.framework.support.ui.BaseFragment;
import com.eaglesakura.android.framework.support.ui.SupportAQuery;
import com.eaglesakura.android.thread.HandlerLoopController;
import com.eaglesakura.android.thread.UIHandler;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

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
    long showingTimeMs;

    HandlerLoopController loopController;

    Intent intent;

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
    }

    /**
     * View状態を更新させる
     */
    void updateView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loopController = new HandlerLoopController(UIHandler.getInstance()) {
            @Override
            protected void onUpdate() {
                updateView();
            }
        };
        loopController.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        loopController.disconnect();
        loopController.dispose();
        loopController = null;
    }

    @Click(R.id.Message_Item_Root)
    void clickItemRoot() {
        // クリックされたらActivityごと閉じる
        getActivity().finish();
    }
}
