package ru.codeoff.bots.sdk.callbacks.commands;

import ru.codeoff.bots.sdk.objects.Message;

/**
 * Custom message callback
 */
public interface OnCommandCallback {

    void OnCommand(Message message);
}
