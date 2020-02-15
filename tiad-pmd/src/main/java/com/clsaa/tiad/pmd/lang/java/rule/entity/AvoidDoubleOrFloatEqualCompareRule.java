package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import net.sourceforge.pmd.lang.java.ast.ASTEqualityExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;

public class AvoidDoubleOrFloatEqualCompareRule extends AbstractJavaRule {

    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final int LIST_SIZE = 2;

    @Override
    public Object visit(ASTEqualityExpression node, Object data) {
        List<ASTPrimaryExpression> list = node.findDescendantsOfType(ASTPrimaryExpression.class);
        if (list.size() != LIST_SIZE) {
            return super.visit(node, data);
        }

        ASTPrimaryExpression left = list.get(0);
        ASTPrimaryExpression right = list.get(1);
        Class<?> leftType = left.getType();
        Class<?> rightType = right.getType();
        if (leftType == null || rightType == null) {
            return super.visit(node, data);
        }

        if (FLOAT.equals(leftType.getName()) && FLOAT.equals(rightType.getName())) {
            ViolationUtils.addViolationWithPrecisePosition(this, node, data);
        } else if (DOUBLE.equals(leftType.getName()) && DOUBLE.equals(rightType.getName())) {
            ViolationUtils.addViolationWithPrecisePosition(this, node, data);
        }
        return super.visit(node, data);
    }
}
