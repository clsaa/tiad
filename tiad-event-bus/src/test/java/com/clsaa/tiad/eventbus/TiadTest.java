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

import com.clsaa.tiad.eventbus.bus.EventBusOptions;
import com.clsaa.tiad.eventbus.bus.EventOptions;
import com.clsaa.tiad.eventbus.enums.ConsumeType;
import com.clsaa.tiad.eventbus.enums.EventFeatures;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class TiadTest {
    public static void main(String[] args) {
        //配置EventBus信息
        EventBusOptions eventBusOptions = new EventBusOptions();
        //配置服务协调组件
        eventBusOptions.setClusterManager(new HazelcastClusterManager());
        //配置运行模式（单机/集群）
        eventBusOptions.setStandalone(false);

        //配置事件特性（可回复、点对点事件）
        EventOptions eventOptions = new EventOptions();
        //可回复事件
        eventOptions.setEventFeatures(EventFeatures.REPLYABLE);
        //点对点事件
        eventOptions.setConsumeType(ConsumeType.POINT_TO_POINT);
        TiadEventDriver.tiad("tiad-app")
                //初始化event bus示例，进行后续链式调用
                .eventBus(eventBusOptions)
                //注册事件订阅者
                .consumer("tiad-topic", "group", event -> {
                    //consumer处理事件
                    System.out.println("I'm consumer, I consumed:" + event.body());
                    //回复事件
                    event.reply("Hi, I know the task has finished. -- from consumer");
                })
                //注册事件发送者
                .send("tiad-topic", "group", "task finished", eventOptions,
                        //处理consumer回复的消息
                        replyEvent -> System.out.println("I'm producer, I consumed: " + replyEvent.result().body())
                );
    }
}
