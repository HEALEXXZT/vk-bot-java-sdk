package ru.codeoff.bots.sdk.callbacks.messages;

import ru.codeoff.bots.sdk.callbacks.Callback;
import ru.codeoff.bots.sdk.objects.Message;

/**
 * Callback for message with some kind of attachments,
 * or if message has no attachments
 */
public interface OnVoiceMessageCallback extends Callback {

    void OnVoiceMessage(Message message);
}

