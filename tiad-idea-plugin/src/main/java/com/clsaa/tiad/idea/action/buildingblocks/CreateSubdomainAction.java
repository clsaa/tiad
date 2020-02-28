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

package com.clsaa.tiad.idea.action.buildingblocks;

import com.clsaa.tiad.buidlingblock.annotation.Subdomain;
import lombok.extern.slf4j.Slf4j;

/**
 * @author clsaa
 */
@Slf4j
public class CreateSubdomainAction extends AbstractCreatePackageBuildingBlockAction {

    @Override
    public Class<Subdomain> getBuildingBlockClass() {
        return Subdomain.class;
    }
}
