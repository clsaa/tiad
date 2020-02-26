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

package com.clsaa.tiad.pmd.lang.java.rule.abstractrule.include;

import com.clsaa.tiad.pmd.lang.java.constances.PackageNames;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractAnnotatableRule;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.JavaNode;

import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public abstract class AbstractIncludeUniqueStereotypesRule extends AbstractAnnotatableRule {

    @Override
    public Object visit(ASTAnnotation node, Object data) {
        final JavaNode typeDeclaration = node.getParent();
        final List<ASTAnnotation> annotations = typeDeclaration.findChildrenOfType(ASTAnnotation.class);
        int count = 0;
        for (ASTAnnotation annotation : annotations) {
            final String typeName = annotation.getType().getTypeName();
            if (typeName.startsWith(PackageNames.ANNOTATION_PACKAGE)) {
                count++;
            }
        }
        if (count > 1) {
            ViolationUtils.addViolationWithPrecisePosition(this, node, data);
        }
        return data;
    }

}