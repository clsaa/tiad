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

public class Main {
    public static void main(String[] args) {
        final VertxOptions vertxOptions = new VertxOptions();
        Vertx vertx = Vertx.vertx(vertxOptions);
        MyVerticle myVerticle = new MyVerticle();
        vertx.deployVerticle(myVerticle);
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("news.uk.sport", message -> {
            System.out.println("I have received a message: " + message.body());
            message.reply("213");
        });
        eventBus.send("news.uk.sport", "Yay! Someone kicked a ball", event -> {
            System.out.println("reply" + event.result().body());
        });
    }
}
