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

package com.clsaa.tiad.pmd.lang.java.constances;


import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Entity;

/**
 * @author clsaa
 */
public interface PackageNames {
    String ENTITY = Entity.class.getSimpleName().toLowerCase();
    String AGGREGATE = Aggregate.class.getSimpleName().toLowerCase();
    String AGGREGATE_SUFFIX = "domain.model";
    String CONTEXT_MAPPING = "contextmapping";
}
