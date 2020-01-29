package org.jnity.vkbot.keyboard;

public enum ButtonColor {
    PRIMARY("primary"),
    DEFAULT("default"),
    NEGATIVE("negative"),
    POSITIVE("positive");

    private String json;

    private ButtonColor(String json) {
        this.json = json;
    }

    public String toJSON() {
        return this.json;
    }
}