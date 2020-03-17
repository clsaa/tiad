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

package com.clsaa.tiad.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Main2 {
    public static void main(String[] args) {
        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions()
                .setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                EventBus eventBus = vertx.eventBus();
                System.out.println("We now have a clustered event bus: " + eventBus);
//                eventBus.consumer("topic", m -> {
//                    System.out.println("I have received a message: " + m.body());
//                });
                eventBus.send("topic", "message", msg -> {
                    System.out.println(msg.result().body());
                });
            } else {
                System.out.println("Failed: " + res.cause());
            }
        });
    }
}
