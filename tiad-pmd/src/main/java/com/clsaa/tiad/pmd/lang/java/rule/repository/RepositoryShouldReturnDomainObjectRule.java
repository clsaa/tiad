package com.clsaa.tiad.pmd.lang.java.rule.repository;

import com.clsaa.tiad.buidlingblock.annotation.Repository;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Entity;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractAnnotatableRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.BuildBlockUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.*;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class RepositoryShouldReturnDomainObjectRule extends AbstractAnnotatableRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Repository.class;
    }

    @Override
    public Object visit(ASTAnnotation node, Object data) {
        final JavaNode typeDeclaration = node.getParent();
        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration = typeDeclaration.findChildrenOfType(ASTClassOrInterfaceDeclaration.class).get(0);
        final List<ASTMethodDeclaration> methods = classOrInterfaceDeclaration.findDescendantsOfType(ASTMethodDeclaration.class);
        if (methods.isEmpty()) {
            return data;
        }
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return data;
        }
        for (ASTMethodDeclaration method : methods) {
            if (!method.isPublic()) {
                continue;
            }
            final ASTResultType resultType = method.getResultType();
            final Entity entity = ASTUtils.findInStructure(buildBlockStructure, Entity.class, resultType);
            if (entity != null) {
                return data;
            } else {
                ViolationUtils.addViolationWithPrecisePosition(this, resultType, data);
            }
        }
        return data;
    }
}