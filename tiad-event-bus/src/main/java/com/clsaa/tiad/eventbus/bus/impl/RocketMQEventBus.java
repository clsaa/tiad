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

package com.clsaa.tiad.eventbus.bus.impl;

import com.clsaa.tiad.eventbus.bus.EventBus;
import com.clsaa.tiad.eventbus.bus.EventBusOptions;
import com.clsaa.tiad.eventbus.bus.EventOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public class RocketMQEventBus implements DistributeEventBus {

    @Override
    public EventBus send(String topic, String group, Object event, EventOptions options) {
        return null;
    }

    @Override
    public <T> EventBus send(String topic, String group, Object event, EventOptions options, Handler<AsyncResult<Message<T>>> replyHandler) {
        return null;
    }

    @Override
    public <T> EventBus consumer(String topic, String group, Handler<Message<T>> handler) {
        return null;
    }

    @Override
    public EventBus init(EventBusOptions eventBusOptions) {
        return this;
    }
}
