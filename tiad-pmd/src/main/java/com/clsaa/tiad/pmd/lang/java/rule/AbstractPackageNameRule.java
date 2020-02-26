package com.clsaa.tiad.pmd.lang.java.rule;

import com.clsaa.tiad.pmd.lang.java.constances.DefaultStrings;
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