package org.jnity.vkbot.keyboard;

import org.json.JSONObject;

public class Button {
    private static final JSONObject EMPTY_PAYLOAD = new JSONObject();
    private final String label;
    private final ButtonColor color;
    private final ButtonType type;
    private final String link;
    private final JSONObject payload;

    public Button(String label, ButtonColor color, ButtonType type, String link, JSONObject payload) {
        this.label = label;
        this.color = color;
        this.type = type;
        this.link = link;
        this.payload = payload;
    }

    public Button(String label) {
        this(label, ButtonColor.DEFAULT, ButtonType.TEXT,"",EMPTY_PAYLOAD);
    }

    public Button(String label, ButtonColor color) {
        this(label, color,ButtonType.TEXT,"", EMPTY_PAYLOAD);
    }

    public Button(String label, JSONObject payload) {
        this(label, ButtonColor.DEFAULT, ButtonType.TEXT,"",payload);
    }

    public Button(String label, ButtonColor color, String link) {
        this(label, color, ButtonType.OPEN_LINK, link, EMPTY_PAYLOAD);
    }

    public Button(String label, String link, JSONObject payload) {
        this(label, ButtonColor.DEFAULT, ButtonType.OPEN_LINK, link, payload);
    }

    public Button(String label, String link) {
        this(label, ButtonColor.DEFAULT, ButtonType.OPEN_LINK, link, EMPTY_PAYLOAD);
    }
    public Button(String label, ButtonColor color, String link ,JSONObject payload) {
        this(label, color, ButtonType.OPEN_LINK, link, payload);
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        JSONObject action = new JSONObject();
        if (this.type.toString().toLowerCase().equals("text")) {
            action.put("type", "text");
            action.put("label", this.label);
            action.put("payload", this.payload.toString());
            result.put("action", action);
            result.put("color", this.color.toJSON());
            return result;
        }
            if (this.type.toString().toLowerCase().equals("open_link")) {
            action.put("type", "open_link");
            action.put("link", this.link);
            action.put("label", this.label);
            action.put("payload", this.payload.toString());
            result.put("action", action);
            //result.put("color", this.color.toJSON());
            return result;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", type.toString());
        return jsonObject;
    }

    public String toString() {
        return this.toJSON().toString();
    }
}