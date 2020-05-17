/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.eventbus.bus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

/**
 * Event Bus Interface
 */
public interface EventBus {
    /**
     * @param topic   topic to send
     * @param group   group of producer
     * @param event   event
     * @param options options of delivery
     * @return instance of event bus
     */
    EventBus send(String topic, String group, Object event, EventOptions options);

    /**
     * @param topic        topic to send
     * @param group        group of producer
     * @param event        event
     * @param options      options of delivery
     * @param replyHandler handle received reply event
     * @return instance of event bus
     */
    <T> EventBus send(String topic, String group, Object event, EventOptions options, Handler<AsyncResult<Message<T>>> replyHandler);

    /**
     * @param topic   topic to subscribe
     * @param group   group of consumer
     * @param handler handle received event
     * @return instance of event bus
     */
    <T> EventBus consumer(String topic, String group, Handler<Message<T>> handler);

    /**
     * init the event bus
     *
     * @param eventBusOptions
     */
    EventBus init(EventBusOptions eventBusOptions);
}
