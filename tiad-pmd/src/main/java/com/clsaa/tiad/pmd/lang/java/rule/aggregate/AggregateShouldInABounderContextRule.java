package com.clsaa.tiad.pmd.lang.java.rule.aggregate;

import com.clsaa.tiad.buidlingblock.annotation.Aggregate;
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
public class AggregateShouldInABounderContextRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTPackageDeclaration node, Object data) {
        final ASTAnnotation aggregateAnnotation = ASTUtils.findFirstAnnotationForPackage(node, Aggregate.class);
        if (aggregateAnnotation == null) {
            return super.visit(node, data);
        }
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final List<BoundedContext> boundedContexts = buildBlockStructure.getByClass(BoundedContext.class);
        final String packageNameImage = node.getPackageNameImage();
        for (BoundedContext boundedContext : boundedContexts) {
            final String siteOfBc = boundedContext.getLocation().getPackageName();
            if (packageNameImage.contains(siteOfBc)) {
                return super.visit(node, data);
            }
        }
        ViolationUtils.addViolationWithPrecisePosition(this, aggregateAnnotation, data);
        return super.visit(node, data);
    }
}