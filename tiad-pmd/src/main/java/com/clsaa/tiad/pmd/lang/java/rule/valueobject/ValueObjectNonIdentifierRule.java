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

import com.clsaa.tiad.buidlingblock.annotation.Identifier;
import com.clsaa.tiad.buidlingblock.annotation.ValueObject;
import com.clsaa.tiad.pmd.lang.java.constances.FiledNames;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;

import java.util.List;
import java.util.Set;

/**
 * @author clsaa
 */
@Slf4j
public class ValueObjectNonIdentifierRule extends AbstractTiadRule {
    private static Set<String> IDENTIFIER_MEANS_FILED_NAME = ImmutableSet
            .of(FiledNames.ID, FiledNames.UNIQUE_CODE, FiledNames.IDENTIFIER);

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final ASTAnnotation valueObjectAnnotation = ASTUtils.findFirstAnnotation(node, ValueObject.class);
        if (valueObjectAnnotation == null) {
            return super.visit(node, data);
        }
        final List<ASTAnnotation> identifierAnnotations = ASTUtils.findDescendantsAnnotations(node, Identifier.class);
        if (identifierAnnotations.size() != 0) {
            ViolationUtils.addViolationWithPrecisePosition(this, valueObjectAnnotation, data);
            return super.visit(node, data);
        }
        final List<ASTFieldDeclaration> fieldDeclarations = node.findDescendantsOfType(ASTFieldDeclaration.class);
        boolean hasIdentifier = false;
        for (ASTFieldDeclaration fieldDeclaration : fieldDeclarations) {
            final String filedName = fieldDeclaration.getFirstDescendantOfType(ASTVariableDeclaratorId.class).getVariableName();
            if (IDENTIFIER_MEANS_FILED_NAME.contains(filedName)) {
                hasIdentifier = true;
                ViolationUtils.addViolationWithPrecisePosition(this, fieldDeclaration, data);
            }
        }
        if (hasIdentifier) {
            ViolationUtils.addViolationWithPrecisePosition(this, valueObjectAnnotation, data);
        }
        return super.visit(node, data);
    }
}
