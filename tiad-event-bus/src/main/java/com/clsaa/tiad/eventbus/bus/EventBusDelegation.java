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

import com.clsaa.tiad.eventbus.route.RouterManager;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBusDelegation implements EventBus {

    private RouterManager routerManager;

    public EventBusDelegation(RouterManager routerManager) {
        this.routerManager = routerManager;
    }

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
}
