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

package com.clsaa.tiad.pmd.lang.java.rule;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.BuildBlockUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;

import java.util.List;
import java.util.Objects;

/**
 * @author clsaa
 */
public abstract class AbstractShouldInRule extends AbstractAnnotatableRule {
    @Override
    public Object visit(ASTAnnotation node, Object data) {
        final String packageNameImage = Objects.requireNonNull(ASTUtils.getPackageDeclaration(node)).getPackageNameImage();
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final List<? extends BuildingBlock> buildingBlocks = buildBlockStructure.getByClass(this.getTargetScope());
        for (BuildingBlock buildingBlock : buildingBlocks) {
            final String bcPackageName = buildingBlock.getLocation().getPackageName();
            if (packageNameImage.startsWith(bcPackageName)) {
                return super.visit(node, data);
            }
        }
        ViolationUtils.addViolationWithPrecisePosition(this, node, data);
        return super.visit(node, data);
    }

    abstract Class<? extends BuildingBlock> getTargetScope();
}
