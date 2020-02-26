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

package com.clsaa.tiad.pmd.lang.java.rule.abstractrule.name;

import com.clsaa.tiad.pmd.lang.java.constances.DefaultStrings;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractAnnotatableRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;

import java.util.Objects;

/**
 * @author clsaa
 */
@Slf4j
public abstract class AbstractPackageNameRule extends AbstractAnnotatableRule {
    @Override
    public Object visit(ASTAnnotation node, Object data) {
        String packageNameImage = Objects.requireNonNull(ASTUtils.getPackageDeclaration(node)).getPackageNameImage();
        final Node parent = node.getParent();
        if (parent instanceof ASTPackageDeclaration) {
            final int lastIndexOf = packageNameImage.lastIndexOf(DefaultStrings.DOT);
            packageNameImage = packageNameImage.substring(0, lastIndexOf);
        }
        if (packageNameImage.endsWith(getPackageSuffix())) {
            return null;
        }
        ViolationUtils.addViolationWithPrecisePosition(this, node, data);
        return null;
    }

    abstract public String getPackageSuffix();
}