package ru.codeoff.bots.keyboard;

/**
 * The enum Button type.
 */
public enum ButtonType {

    /**
     * Open link button type.
     */
    OPEN_LINK("open_link"),

    /**
     * Text button type.
     */
    TEXT("text"),

    /**
     * Vkpay button type.
     */
    VKPAY("vkpay");


    private String json;


    private ButtonType(String json) {
        this.json = json;
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJSON() {
        return this.json;
    }

}
