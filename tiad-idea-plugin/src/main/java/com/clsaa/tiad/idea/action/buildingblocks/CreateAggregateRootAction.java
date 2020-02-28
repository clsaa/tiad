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

import com.clsaa.tiad.buidlingblock.annotation.AggregateRoot;
import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.buidlingblock.annotation.Identifier;
import com.clsaa.tiad.idea.constances.Icons;

import javax.swing.*;

/**
 * @author clsaa
 */
public class CreateAggregateRootAction extends AbstractCreateClassBuildingBlockAction {
    @Override
    public Class<AggregateRoot> getBuildingBlockClass() {
        return AggregateRoot.class;
    }

    @Override
    public String getImportPackages() {
        String result = "import " + AggregateRoot.class.getName() + ";";
        result += "\n";
        result += "import " + Entity.class.getName() + ";";
        result += "\n";
        result += "import " + Identifier.class.getName() + ";";
        return result;
    }

    @Override
    public String getAnnotations() {
        String result = "@" + AggregateRoot.class.getSimpleName();
        result += "\n";
        result += "@" + Entity.class.getSimpleName();
        return result;
    }

    @Override
    public Icon getIcon() {
        return Icons.AGGREGATE_ROOT;
    }
}
