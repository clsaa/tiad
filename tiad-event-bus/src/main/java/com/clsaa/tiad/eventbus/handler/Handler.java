package com.clsaa.tiad.eventbus.handler;

/**
 * A generic event handler.
 */
@FunctionalInterface
public interface Handler<E> {

    /**
     * Something has happened, so handle it.
     *
     * @param event the event to handle
     */
    void handle(E event);
}