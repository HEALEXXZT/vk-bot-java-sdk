package ru.codeoff.bots.message;

/**
 * The interface Message handler.
 */
public interface MessageHandler {

    /**
     * Execute.
     *
     * @param sender the sender
     * @param args   the args
     */
    void execute(final Integer sender, final String[] args);
}
