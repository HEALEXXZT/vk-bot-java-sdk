package ru.codeoff.bots.keyboard;

/**
 * The enum Button color.
 */
public enum ButtonColor {

    /**
     * Primary button color.
     */
    PRIMARY("primary"),

    /**
     * Default button color.
     */
    DEFAULT("default"),

    /**
     * Negative button color.
     */
    NEGATIVE("negative"),

    /**
     * Positive button color.
     */
    POSITIVE("positive");

    private String json;

    private ButtonColor(String json) {
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