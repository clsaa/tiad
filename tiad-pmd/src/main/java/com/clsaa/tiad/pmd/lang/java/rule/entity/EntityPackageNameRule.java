package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.pmd.lang.java.constances.PackageNames;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;

import java.util.*;

/**
 * @author clsaa
 */
@Slf4j
public class EntityPackageNameRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final ASTAnnotation entityAnnotation = ASTUtils.findFirstAnnotation(node, Entity.class);
        if (entityAnnotation == null) {
            return super.visit(node, data);
        }
        final List<String> packageList = node.getQualifiedName().getPackageList();
        if (packageList.isEmpty()) {
            ViolationUtils.addViolationWithPrecisePosition(this, entityAnnotation, data);
        } else {
            final String sitePackageName = packageList.get(packageList.size() - 1);
            if (!sitePackageName.equals(PackageNames.ENTITY)) {
                ViolationUtils.addViolationWithPrecisePosition(this, entityAnnotation, data);
            }
        }
        return super.visit(node, data);
    }
}