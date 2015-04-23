package com.eaglesakura.andriders.enduro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eaglesakura.andriders.AcesEnvironment;
import com.eaglesakura.andriders.command.CommandSetupResultBuilder;
import com.eaglesakura.andriders.command.TeamOrderResultBuilder;
import com.eaglesakura.andriders.enduro.data.MessageIntentBuilder;
import com.eaglesakura.android.framework.support.ui.BaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * トリガーからメッセージ送信の内容を指定するActivity
 */
@EActivity(R.layout.activity_message_setting)
public class MessageSettingActivity extends BaseActivity {

    @ViewById(R.id.Trigger_InputMessage_Text)
    EditText inputMessage;

    boolean orderMode = false;

    @ViewById(R.id.Trigger_InputMessage_Commit)
    Button commitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final String scheme = getIntent().getData().getScheme();
        orderMode = scheme.equals(AcesEnvironment.SCHEMA_TRIGGER_ORDER);
        if (orderMode) {
            commitButton.setText("オーダー送信");
        }
    }

    String getInputMessage() {
        return inputMessage.getText().toString();
    }

    @Click(R.id.Trigger_InputMessage_Commit)
    void commit() {
        MessageIntentBuilder intentBuilder = MessageIntentBuilder.newTriggerMessage(this);
        intentBuilder.message(getInputMessage());

        if (orderMode) {
            // チームオーダーとして返す
            TeamOrderResultBuilder builder = new TeamOrderResultBuilder(this);
            builder.setRemoteIntent(intentBuilder.buildBootIntent());
            builder.finish();
        } else {
            // 普通のコマンドとして返す
            CommandSetupResultBuilder builder = new CommandSetupResultBuilder(this);
            builder.icon(R.drawable.ic_launcher);

            // ACEsに起動させるIntentを組み立てる
            {
                builder.intent(intentBuilder.build());
            }

            // finish
            builder.finish();
        }
    }
}
