package ru.codeoff.bots.sdk.callbacks.callbackapi.market;

import ru.codeoff.bots.sdk.callbacks.Callback;
import org.json.JSONObject;

/**
 * See more: <a href="https://vk.com/dev/callback_api">link</a>.
 */
public interface OnMarketCommentNewCallback extends Callback {

    void callback(JSONObject object);
}