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

import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import lombok.Data;

@Data
public class EventBusOptions {
    private ClusterManager clusterManager;
    private VertxOptions vertxOptions;
    private boolean standalone;
}
