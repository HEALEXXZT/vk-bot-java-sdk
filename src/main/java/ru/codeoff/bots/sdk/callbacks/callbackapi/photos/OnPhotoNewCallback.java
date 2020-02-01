package ru.codeoff.bots.sdk.callbacks.callbackapi.photos;

import ru.codeoff.bots.sdk.callbacks.Callback;
import org.json.JSONObject;

/**
 * See more: <a href="https://vk.com/dev/callback_api">link</a>.
 */
public interface OnPhotoNewCallback extends Callback {

    void callback(JSONObject object);
}