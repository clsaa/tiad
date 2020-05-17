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

import com.clsaa.tiad.eventbus.bus.EventBus;
import com.clsaa.tiad.eventbus.bus.EventBusDelegation;
import com.clsaa.tiad.eventbus.bus.EventBusOptions;
import com.clsaa.tiad.eventbus.route.RouterManager;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TiadEventDriver {
    private String appName;
    private String instanceId;
    private EventBus eventBus;

    public TiadEventDriver(String appName) {
        this.appName = appName;
        this.instanceId = UUID.randomUUID().toString();
    }


    public EventBus eventBus() {
        RouterManager routerManager = new RouterManager();
        this.eventBus = new EventBusDelegation(routerManager);
        return eventBus;
    }

    public EventBus eventBus(EventBusOptions eventBusOptions) {
        RouterManager routerManager = new RouterManager(eventBusOptions);
        this.eventBus = new EventBusDelegation(routerManager);
        return eventBus;
    }

    public static TiadEventDriver tiad(String appName) {
        return new TiadEventDriver(appName);
    }
}
