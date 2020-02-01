package ru.codeoff.bots.sdk.callbacks.callbackapi.videos;

import ru.codeoff.bots.sdk.callbacks.Callback;
import org.json.JSONObject;

/**
 * See more: <a href="https://vk.com/dev/callback_api">link</a>.
 */
public interface OnVideoCommentEditCallback extends Callback {

    void callback(JSONObject object);
}