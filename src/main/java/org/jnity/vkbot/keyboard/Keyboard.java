package org.jnity.vkbot.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

public class Keyboard {
    private static final int MAX_BUTTONS_IN_LINES = 4;
    private static final int MAX_LINES = 10;
    private static final int MAX_BUTTONS = 40;
    private boolean one_time = false;
    private boolean inline = false;
    private List<List<Button>> buttonsLines = new ArrayList();

    public Keyboard setOneTime(boolean value) {
        this.one_time = value;
        return this;
    }

    public Keyboard setInline(boolean value) {
        this.inline = value;
        return this;
    }

    public Keyboard addButtonsLine(List<Button> buttons) {
        if (buttons.size() > 4) {
            throw new IllegalArgumentException("Too many buttons per line, max is 4");
        } else if (this.buttonsLines.size() == 10) {
            throw new IllegalStateException("Lines limit reached buttons, max is10");
        } else {
            this.buttonsLines.add(buttons);
            return this;
        }
    }

    public Keyboard addButtonsLine(Button... buttons) {
        return this.addButtonsLine(Arrays.asList(buttons));
    }

    public Keyboard addButtonsLine(String... buttons) {
        return this.addButtonsLine((List)Arrays.stream(buttons).map(Button::new).collect(Collectors.toList()));
    }

    public Keyboard addButtons(List<Button> buttons) {
        if (this.buttonsLines.stream().mapToInt(List::size).sum() + buttons.size() > 40) {
            throw new IllegalArgumentException("Too many buttons, max is 40");
        } else {
            int lineCounter = 0;
            List<Button> buttonsLine = new ArrayList();
            Iterator var4 = buttons.iterator();

            while(var4.hasNext()) {
                Button button = (Button)var4.next();
                buttonsLine.add(button);
                ++lineCounter;
                if (lineCounter == 4) {
                    this.buttonsLines.add(buttonsLine);
                    lineCounter = 0;
                    buttonsLine = new ArrayList();
                }
            }

            if (lineCounter > 0) {
                this.buttonsLines.add(buttonsLine);
            }

            return this;
        }
    }

    public Keyboard addButtons(Button... buttons) {
        return this.addButtons(Arrays.asList(buttons));
    }

    public Keyboard addButtons(String... buttons) {
        return this.addButtons((List)Arrays.stream(buttons).map(Button::new).collect(Collectors.toList()));
    }

    public static Keyboard of(String... buttons) {
        Keyboard result = new Keyboard();
        result.addButtons(buttons);
        return result;
    }

    public static Keyboard of(Button... buttons) {
        Keyboard result = new Keyboard();
        result.addButtons(buttons);
        return result;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        JSONArray buttons = new JSONArray();
        Iterator var3 = this.buttonsLines.iterator();

        while(var3.hasNext()) {
            List<Button> line = (List)var3.next();
            JSONArray jsonLine = new JSONArray();
            Iterator var6 = line.iterator();

            while(var6.hasNext()) {
                Button button = (Button)var6.next();
                jsonLine.put(button.toJSON());
            }

            buttons.put(jsonLine);
        }

        result.put("buttons", buttons);
        if (this.inline) {
            result.put("inline", this.inline);
        }else {
            result.put("one_time", this.one_time);
        }
        result.put("one_time", this.one_time);
        return result;
    }

    public String toString() {
        return this.toJSON().toString();
    }
}