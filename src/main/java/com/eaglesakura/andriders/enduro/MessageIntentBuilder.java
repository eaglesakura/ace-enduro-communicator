package com.eaglesakura.andriders.enduro;

import android.content.Context;

import com.eaglesakura.andriders.command.RemoteIntentBuilder;
import com.eaglesakura.andriders.protocol.CommandProtocol;
import com.eaglesakura.android.annotations.AnnotationUtil;

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
        bootIntentBuilder = RemoteIntentBuilder.newService(context, ReceivedMessageProxyService.class, ReceivedMessageProxyService.ACTION_SHOW_MESSAGE);
    }

    /**
     * 遠隔端末に表示するメッセージを指定
     *
     * @param message
     *
     * @return
     */
    public MessageIntentBuilder message(String message) {
        bootIntentBuilder.putExtra(ReceivedMessageProxyService.EXTRA_MESSAGE, message);
        return this;
    }

    /**
     * ダイアログとして表示する
     *
     * @return
     */
    public MessageIntentBuilder enableDialog() {
        bootIntentBuilder.putExtra(ReceivedMessageProxyService.EXTRA_BOOT_DIALOG, true);
        return this;
    }

    /**
     * ステータスバーを表示する
     *
     * @return
     */
    public MessageIntentBuilder enableStatusbar() {
        bootIntentBuilder.putExtra(ReceivedMessageProxyService.EXTRA_NOTIFICATION_STATUSBAR, true);
        return this;
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
