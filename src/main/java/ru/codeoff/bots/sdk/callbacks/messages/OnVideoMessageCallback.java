package ru.codeoff.bots.sdk.callbacks.messages;

import ru.codeoff.bots.sdk.callbacks.Callback;
import ru.codeoff.bots.sdk.objects.Message;

/**
 * Callback for message with some kind of attachments,
 * or if message has no attachments
 */
public interface OnVideoMessageCallback extends Callback {

    void onVideoMessage(Message message);
}
