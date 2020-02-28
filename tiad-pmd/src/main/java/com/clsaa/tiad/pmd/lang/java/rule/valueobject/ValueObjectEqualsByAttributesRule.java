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

package com.clsaa.tiad.pmd.lang.java.rule.valueobject;

import com.clsaa.tiad.buidlingblock.annotation.ValueObject;
import com.clsaa.tiad.pmd.lang.java.constances.MethodNames;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;

import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class ValueObjectEqualsByAttributesRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final ASTAnnotation valueObjectAnnotation = ASTUtils.findFirstAnnotation(node, ValueObject.class);
        if (valueObjectAnnotation == null) {
            return super.visit(node, data);
        }
        List<ASTMethodDeclaration> descendantsOfType = node.findDescendantsOfType(ASTMethodDeclaration.class);
        boolean hasEquals = false;
        boolean hasHashCode = false;
        for (ASTMethodDeclaration astMethodDeclaration : descendantsOfType) {
            final String methodName = astMethodDeclaration.getMethodName();
            if (MethodNames.EQUALS.equals(methodName) && astMethodDeclaration.isPublic()) {
                hasEquals = true;
            }
            if (MethodNames.HASH_CODE.equals(methodName) && astMethodDeclaration.isPublic()) {
                hasHashCode = true;
            }
        }

        final ASTAnnotation lombokDataAnnotation = ASTUtils.findFirstAnnotation(node, Data.class);
        final ASTAnnotation lombokEqualsAndHashCodeAnnotation = ASTUtils.findFirstAnnotation(node, EqualsAndHashCode.class);
        if (lombokDataAnnotation != null || lombokEqualsAndHashCodeAnnotation != null) {
            hasHashCode = true;
            hasEquals = true;
        }


        if (!hasHashCode || !hasEquals) {
            ViolationUtils.addViolationWithPrecisePosition(this, valueObjectAnnotation, data);
        }

        return super.visit(node, data);
    }
}
