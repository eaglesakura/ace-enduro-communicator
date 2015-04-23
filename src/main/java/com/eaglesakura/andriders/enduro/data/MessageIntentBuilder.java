package com.eaglesakura.andriders.enduro.data;

import android.content.Context;
import android.util.Base64;

import com.eaglesakura.andriders.command.RemoteIntentBuilder;
import com.eaglesakura.andriders.enduro.EnduroExtensionService;
import com.eaglesakura.andriders.enduro.ReceiveMessageActivity;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.android.annotations.AnnotationUtil;
import com.eaglesakura.util.StringUtil;

/**
 * 送信用のRemoteIntentを組み立てる
 */
public class MessageIntentBuilder {
    /**
     * 相手端末に送信するためのIntent
     */
    RemoteIntentBuilder sendIntentBuilder;

    /**
     * 相手端末で表示を行うためのIntent
     */
    final RemoteIntentBuilder bootIntentBuilder;

    final Context context;

    private MessageIntentBuilder(Context context) {
        this.context = context;
        bootIntentBuilder = RemoteIntentBuilder.newActivity(context, AnnotationUtil.annotation(ReceiveMessageActivity.class), ReceiveMessageActivity.ACTION_SHOW_MESSAGE);
    }

    /**
     * 遠隔端末に表示するメッセージを指定
     *
     * @param message
     *
     * @return
     */
    public MessageIntentBuilder message(String message) {
        bootIntentBuilder.putExtra(ReceiveMessageActivity.EXTRA_DIALOG_MESSAGE, message);
        return this;
    }

    /**
     * ダイアログとして表示する
     *
     * @return
     */
    public MessageIntentBuilder enableDialog() {
        bootIntentBuilder.putExtra(ReceiveMessageActivity.EXTRA_DIALOG_ENABLE, true);
        return this;
    }

    /**
     * ステータスバーを表示する
     *
     * @return
     */
    public MessageIntentBuilder enableStatusbar() {
        bootIntentBuilder.putExtra(ReceiveMessageActivity.EXTRA_STATUSBAR_ENABLE, true);
        return this;
    }


    /**
     * 相手端末に送信するIntent本体を生成する
     *
     * @return
     */
    public CommandProtocol.IntentPayload buildBootIntent() {
        return bootIntentBuilder.build();
    }

    /**
     * Serviceを起動させるためのRemoteIntentを生成する
     *
     * @return
     */
    public CommandProtocol.IntentPayload build() {
        sendIntentBuilder.putExtra(EnduroExtensionService.EXTRA_REMOTE_INTENT, bootIntentBuilder.build().toByteArray());

        return sendIntentBuilder.build();
    }

    /**
     * トリガーから起動するためのBuilderを生成する
     *
     * @param context
     *
     * @return
     */
    public static MessageIntentBuilder newTriggerMessage(Context context) {
        MessageIntentBuilder result = new MessageIntentBuilder(context.getApplicationContext());
        // トリガーから送信する場合は全メンバーに強制送信する
        result.sendIntentBuilder =
                RemoteIntentBuilder.newService(context, AnnotationUtil.annotation(EnduroExtensionService.class), EnduroExtensionService.ACTION_SEND_INTENT_TO_ALLMEMBERS);
        return result;
    }
}
