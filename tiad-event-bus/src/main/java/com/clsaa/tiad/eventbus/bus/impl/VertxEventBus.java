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
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.spi.cluster.ClusterManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class VertxEventBus implements DistributeEventBus, LocalEventBus {
    private ClusterManager defaultClusterManager;
    private Vertx vertx;
    private VertxOptions options;
    private io.vertx.core.eventbus.EventBus eventBus;
    private volatile boolean inited = false;

    @Override
    public EventBus init(EventBusOptions eventBusOptions) {
        this.options = eventBusOptions.getVertxOptions();

        if (eventBusOptions.isStandalone()) {
            this.vertx = Vertx.vertx();
            this.eventBus = vertx.eventBus();
            inited = true;
            log.info("vert.x standalone init success");
            return this;
        }

        this.defaultClusterManager = eventBusOptions.getClusterManager();
        options.setClusterManager(this.defaultClusterManager);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                this.vertx = res.result();
                this.eventBus = this.vertx.eventBus();
                log.info("vert.x cluster init success");
            } else {
                log.error("vert.x cluster init failed", res.cause());
            }
            inited = true;
        });
        int count = 0;
        while (!inited) {
            log.info("wait inited " + count++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public EventBus send(String topic, String group, Object event, EventOptions options) {
        this.eventBus.send(topic, event);
        return this;
    }

    @Override
    public <T> EventBus send(String topic, String group, Object event, EventOptions options, Handler<AsyncResult<Message<T>>> replyHandler) {
        this.eventBus.send(topic, event, replyHandler);
        return this;
    }

    @Override
    public <T> EventBus consumer(String topic, String group, Handler<Message<T>> handler) {
        this.eventBus.consumer(topic, handler);
        return this;
    }
}
