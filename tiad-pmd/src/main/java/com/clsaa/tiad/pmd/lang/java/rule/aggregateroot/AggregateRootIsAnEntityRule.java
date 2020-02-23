package com.clsaa.tiad.pmd.lang.java.rule.aggregateroot;

import com.clsaa.tiad.buidlingblock.annotation.AggregateRoot;
import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;

/**
 * @author clsaa
 */
@Slf4j
public class AggregateRootIsAnEntityRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final ASTAnnotation aggregateAnnotation = ASTUtils.findFirstAnnotation(node, AggregateRoot.class);
        if (aggregateAnnotation == null) {
            return super.visit(node, data);
        }
        final ASTAnnotation entityAnnotation = ASTUtils.findFirstAnnotation(node, Entity.class);
        if (entityAnnotation == null) {
            ViolationUtils.addViolationWithPrecisePosition(this, aggregateAnnotation, data);
        }
        return super.visit(node, data);
    }
}