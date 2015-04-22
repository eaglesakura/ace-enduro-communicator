package com.eaglesakura.andriders.enduro;

import android.os.Bundle;
import android.widget.EditText;

import com.eaglesakura.andriders.command.CommandSetupResultBuilder;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String getInputMessage() {
        return inputMessage.getText().toString();
    }

    @Click(R.id.Trigger_InputMessage_Commit)
    void commit() {
        CommandSetupResultBuilder builder = new CommandSetupResultBuilder(this);
        builder.icon(R.drawable.ic_launcher);

        // ACEsに起動させるIntentを組み立てる
        {
            MessageIntentBuilder intentBuilder = MessageIntentBuilder.newTriggerMessage(this);
            intentBuilder.message(getInputMessage());
            builder.intent(intentBuilder.build());
        }

        // finish
        builder.finish();
    }
}
