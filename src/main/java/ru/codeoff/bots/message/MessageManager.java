package ru.codeoff.bots.message;

import java.util.*;

import ru.codeoff.bots.keyboard.Keyboard;
import ru.codeoff.bots.sdk.callbacks.callbackapi.ExecuteCallback;
import ru.codeoff.bots.sdk.clients.Group;
import ru.codeoff.bots.sdk.objects.Message;

/**
 * The type Message manager.
 */
public class MessageManager
{
    private static HashMap<String, MessageHandler> messages;
    private static HashMap<String, String> submessages;
    private static HashMap<String, String> keys;

    /**
     * Register keys.
     *
     * @param keyz    the keyz
     * @param message the message
     */
    public static void registerKeys(final List<String> keyz, final String message) {
        for (final String key : keyz) {
            MessageManager.keys.put(key.toLowerCase(), message);
        }
    }

    /**
     * Register message.
     *
     * @param handler  the handler
     * @param commands the commands
     */
    public static void registerMessage(final MessageHandler handler, final String... commands) {
        for (final String cmd : commands) {
            MessageManager.messages.put(cmd.toLowerCase(), handler);
        }
    }

    /**
     * Register sub message.
     *
     * @param question the question
     * @param answer   the answer
     */
    public static void registerSubMessage(final String question, final String answer) {
        MessageManager.submessages.put(question.toLowerCase(), answer);
    }

    /**
     * Handle.
     *
     * @param message the message
     * @param group   the group
     */
    public static void handle(final Message message, Group group) {
        final String[] args = message.getText().toLowerCase().split(" ");
        if (MessageManager.messages.containsKey(args[0])) {
            MessageManager.messages.get(args[0]).execute(message.authorId(), build(message.getText().replace("[", "").replace("]", "").split(" ")));
            return;
        }
        if (MessageManager.submessages.containsKey(args[0])) {
                sendMessage(message.authorId(), MessageManager.submessages.get(args[0]), group);
            return;
        }
        for (final String str : args) {
            if (MessageManager.keys.containsKey(str)) {
                sendMessage(message.authorId(), MessageManager.keys.get(str), group);
                return;
            }
        }
    }
    
    private static String[] build(final String[] args) {
        final String[] elements = new String[args.length - 1];
        for (int i = 1; i < args.length; ++i) {
            elements[i - 1] = args[i];
        }
        return elements;
    }

    /**
     * Send message.
     *
     * @param author_id the author id
     * @param text      the text
     * @param group     the group
     */
    public static void sendMessage(final int author_id, final String text, Group group) {
        new Message().from(group).to(author_id).text(text).send();
    }

    /**
     * Send message.
     *
     * @param author_id           the author id
     * @param text                the text
     * @param forwardedMessagesID the forwarded messages id
     * @param group               the group
     */
    public static void sendMessage(final int author_id, final String text, int forwardedMessagesID, Group group) {
        new Message().from(group).to(author_id).text(text).forwardedMessages(forwardedMessagesID).send();
    }

    /**
     * Send message.
     *
     * @param author_id the author id
     * @param text      the text
     * @param keyboard  the keyboard
     * @param group     the group
     */
    public static void sendMessage(final int author_id, final String text, final Keyboard keyboard, Group group) {
        new Message().from(group).to(author_id).text(text).keyboard(keyboard).send(new ExecuteCallback[0]);
    }

    /**
     * Send message.
     *
     * @param author_id the author id
     * @param text      the text
     * @param group     the group
     */
    public static void sendMessage(final int author_id, final String[] text, Group group) {
        final StringBuilder builder = new StringBuilder();
        for (final String st : text) {
            builder.append(st).append("\n");
        }
        new Message().from(group).to(author_id).text(builder.toString()).send(new ExecuteCallback[0]);
    }
    
    static {
        MessageManager.messages = new HashMap<String, MessageHandler>();
        MessageManager.submessages = new HashMap<String, String>();
        MessageManager.keys = new HashMap<String, String>();
    }
}
