package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.buidlingblock.annotation.Identifier;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;

import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class EntityMustHaveOneIdentifierRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final ASTAnnotation entityAnnotation = ASTUtils.findFirstAnnotation(node, Entity.class);
        if (entityAnnotation == null) {
            return super.visit(node, data);
        }
        final List<ASTAnnotation> identifierAnnotations = ASTUtils.findDescendantsAnnotations(node, Identifier.class);
        if (identifierAnnotations.size() != 1) {
            ViolationUtils.addViolationWithPrecisePosition(this, entityAnnotation, data);
        }
        return super.visit(node, data);
    }
}