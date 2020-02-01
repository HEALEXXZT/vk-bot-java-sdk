package ru.codeoff.bots.keyboard;

import org.json.JSONObject;

/**
 * The type Button.
 */
public class Button {
    private static final JSONObject EMPTY_PAYLOAD = new JSONObject();
    private final String hash;
    private final String label;
    private final ButtonColor color;
    private final ButtonType type;
    private final String link;
    private final JSONObject payload;


    /**
     * Instantiates a new Button.
     *
     * @param label   the label
     * @param color   the color
     * @param type    the type
     * @param link    the link
     * @param hash    the hash
     * @param payload the payload
     */
    public Button(String label, ButtonColor color, ButtonType type, String link, String hash, JSONObject payload) {
        this.label = label;
        this.color = color;
        this.type = type;
        this.link = link;
        this.hash = hash;
        this.payload = payload;
    }

    /**
     * Instantiates a new Button.
     *
     * @param label the label
     */
    public Button(String label) {
        this(label, ButtonColor.DEFAULT, ButtonType.TEXT,"", "",EMPTY_PAYLOAD);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label the label
     * @param color the color
     */
    public Button(String label, ButtonColor color) {
        this(label, color,ButtonType.TEXT,"", "", EMPTY_PAYLOAD);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label   the label
     * @param payload the payload
     */
    public Button(String label, JSONObject payload) {
        this(label, ButtonColor.DEFAULT, ButtonType.TEXT, "", "", payload);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label   the label
     * @param link    the link
     * @param payload the payload
     */
    public Button(String label, String link, JSONObject payload) {
        this(label, ButtonColor.DEFAULT, ButtonType.OPEN_LINK, link,"", payload);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label the label
     * @param link  the link
     */
    public Button(String label, String link) {
        this(label, ButtonColor.DEFAULT, ButtonType.OPEN_LINK, link,"", EMPTY_PAYLOAD);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label the label
     * @param color the color
     * @param hash  the hash
     */
    public Button(String label, ButtonColor color, String hash) {
        this(label, color, ButtonType.VKPAY, "",hash, EMPTY_PAYLOAD);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label the label
     * @param hash  the hash
     * @param aray  the aray
     */
    public Button(String label, String hash, String aray) {
        this(label, ButtonColor.DEFAULT, ButtonType.VKPAY, "", hash, EMPTY_PAYLOAD);
    }

    /**
     * Instantiates a new Button.
     *
     * @param label   the label
     * @param color   the color
     * @param hash    the hash
     * @param payload the payload
     */
    public Button(String label, ButtonColor color, String hash, JSONObject payload) {
        this(label, color, ButtonType.VKPAY, "", hash, payload);
    }

    /**
     * To json json object.
     *
     * @return the json object
     */
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        JSONObject action = new JSONObject();
        if (this.type.toString().toLowerCase().equals("text")) {
            action.put("type", "text");
            action.put("label", this.label);
            action.put("payload", this.payload.toString());
            //
            result.put("action", action);
            result.put("color", this.color.toJSON());
            return result;
        }
            if (this.type.toString().toLowerCase().equals("open_link")) {
            action.put("type", "open_link");
            action.put("link", this.link);
            action.put("label", this.label);
            action.put("payload", this.payload.toString());
            //
            result.put("action", action);
            return result;
        }
        if (this.type.toString().toLowerCase().equals("vkpay")) {
            action.put("type", "vkpay");
            action.put("hash", this.hash);
            action.put("payload", this.payload.toString());
            //
            result.put("action", action);
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