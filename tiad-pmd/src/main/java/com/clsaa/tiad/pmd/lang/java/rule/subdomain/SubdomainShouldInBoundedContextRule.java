package com.clsaa.tiad.pmd.lang.java.rule.subdomain;

import com.clsaa.tiad.buidlingblock.annotation.Subdomain;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.BoundedContext;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.BuildBlockUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;

import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class SubdomainShouldInBoundedContextRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTPackageDeclaration node, Object data) {
        final ASTAnnotation subdomain = ASTUtils.findFirstAnnotationForPackage(node, Subdomain.class);
        if (subdomain == null) {
            return super.visit(node, data);
        }
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final String packageNameImage = node.getPackageNameImage();
        final List<BoundedContext> boundedContexts = buildBlockStructure.getByClass(BoundedContext.class);
        for (BoundedContext boundedContext : boundedContexts) {
            final String bcPackageName = boundedContext.getLocation().getPackageName();
            if (packageNameImage.startsWith(bcPackageName)) {
                return super.visit(node, data);
            }
        }
        ViolationUtils.addViolationWithPrecisePosition(this, subdomain, data);
        return super.visit(node, data);
    }
}