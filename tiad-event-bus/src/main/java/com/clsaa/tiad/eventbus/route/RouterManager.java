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

package com.clsaa.tiad.eventbus.route;

import com.clsaa.tiad.eventbus.TiadEventDriver;
import com.clsaa.tiad.eventbus.bus.EventBus;
import com.clsaa.tiad.eventbus.bus.EventBusOptions;
import com.clsaa.tiad.eventbus.bus.EventOptions;
import com.clsaa.tiad.eventbus.bus.impl.RocketMQEventBus;
import com.clsaa.tiad.eventbus.bus.impl.VertxEventBus;
import com.clsaa.tiad.eventbus.consumer.Consumer;
import com.clsaa.tiad.eventbus.enums.EventFeatures;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.List;

public class RouterManager {
    private TiadEventDriver tiadEventDriver;
    private EventBus rocketMQEventBus;
    private EventBus vertxEventBus;
    private List<Consumer> consumers;

    public RouterManager() {
        this(new EventBusOptions());
    }

    public RouterManager(EventBusOptions eventBusOptions) {
        rocketMQEventBus = new RocketMQEventBus().init(eventBusOptions);
        vertxEventBus = new VertxEventBus().init(eventBusOptions);
    }

    public EventBus route(String topic, String group, EventOptions eventOptions) {
        if (eventOptions.getEventFeatures().equals(EventFeatures.ORDINAL)) {
            return this.rocketMQEventBus;
        }
        if (eventOptions.getEventFeatures().equals(EventFeatures.PERSISTENCE)) {
            return this.rocketMQEventBus;
        }
        if (eventOptions.getEventFeatures().equals(EventFeatures.TRANSACTIONAL)) {
            return this.rocketMQEventBus;
        }
        if (eventOptions.getEventFeatures().equals(EventFeatures.REPLYABLE)) {
            return this.vertxEventBus;
        }
//        for (Consumer consumer : consumers) {
//            if (consumer.getTopic().equals(topic)) {
//                if (!consumer.getAppName().equals(tiad.getAppName())) {
//                    return vertxEventBus;
//                }
//            }
//        }
        return vertxEventBus;
    }

    public <T> void consumer(String topic, String group, Handler<Message<T>> handler) {
        this.rocketMQEventBus.consumer(topic, group, handler);
        this.vertxEventBus.consumer(topic, group, handler);
    }

}
