package ru.codeoff.bots.sdk.callbacks.messages;

import ru.codeoff.bots.sdk.callbacks.Callback;

/**
 * Callback will be called when user started typing
 */
public interface OnTypingCallback extends Callback {

    void OnTyping(Integer userId);
}

