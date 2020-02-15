package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;

public class EntityMustHaveOneIdentifierRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTMarkerAnnotation node, Object data) {
        Node firstStmt = node.jjtGetChild(1);
        if (!hasBlockAsFirstChild(firstStmt)) {
            addViolation(data, node);
        }
        return super.visit(node, data);
    }

    private boolean hasBlockAsFirstChild(Node node) {
        return (node.jjtGetNumChildren() != 0 && (node.jjtGetChild(0) instanceof ASTBlock));
    }
}