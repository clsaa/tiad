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

package com.clsaa.tiad.pmd.lang.java.rule.domainevent;

import com.clsaa.tiad.buidlingblock.annotation.DomainEvent;
import com.clsaa.tiad.pmd.lang.java.constances.FiledNames;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractAnnotatableRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;

import java.lang.annotation.Annotation;
import java.util.EventObject;
import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class DomainEventShouldHaveSource extends AbstractAnnotatableRule {
    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return DomainEvent.class;
    }

    @Override
    public Object visit(ASTAnnotation node, Object data) {
        final ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration = ASTUtils.findASTClassOrInterfaceDeclarationInParent(node);
        final List<ASTFieldDeclaration> fieldDeclarations = classOrInterfaceDeclaration.findDescendantsOfType(ASTFieldDeclaration.class);
        for (ASTFieldDeclaration fieldDeclaration : fieldDeclarations) {
            if (fieldDeclaration.getVariableName().equals(FiledNames.SOURCE) && !fieldDeclaration.isStatic()) {
                return data;
            }
        }
        final ASTClassOrInterfaceType superClassTypeNode = classOrInterfaceDeclaration.getSuperClassTypeNode();
        final List<ASTClassOrInterfaceDeclaration> declarations = superClassTypeNode.findChildrenOfType(ASTClassOrInterfaceDeclaration.class);
        for (ASTClassOrInterfaceDeclaration declaration : declarations) {
            if (declaration.getType().getName().equals(EventObject.class.getName())) {
                return data;
            }
        }
        ViolationUtils.addViolationWithPrecisePosition(this, node, data);
        return data;
    }

}