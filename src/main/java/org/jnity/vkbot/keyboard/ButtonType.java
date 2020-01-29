package org.jnity.vkbot.keyboard;

public enum ButtonType {
    OPEN_LINK("open_link"),
    TEXT("text");

    private String json;

    private ButtonType(String json) {
        this.json = json;
    }

    public String toJSON() {
        return this.json;
    }

}
