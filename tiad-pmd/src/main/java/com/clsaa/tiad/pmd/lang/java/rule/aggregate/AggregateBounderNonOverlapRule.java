package com.clsaa.tiad.pmd.lang.java.rule.aggregate;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
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
public class AggregateBounderNonOverlapRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTPackageDeclaration node, Object data) {
        final ASTAnnotation aggregateAnnotation = ASTUtils.findFirstDescendantsAnnotation(node, com.clsaa.tiad.buidlingblock.annotation.Aggregate.class);
        if (aggregateAnnotation == null) {
            return super.visit(node, data);
        }
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final List<Aggregate> aggregates = buildBlockStructure.getByClass(Aggregate.class);
        final String packageNameImage = node.getPackageNameImage();
        for (Aggregate aggregate : aggregates) {
            final String packageName = aggregate.getLocation().getPackageName();
            if (packageName.equals(packageNameImage)) {
                continue;
            }
            if (packageName.contains(packageNameImage) || packageNameImage.contains(packageName)) {
                ViolationUtils.addViolationWithPrecisePosition(this, aggregateAnnotation, data);
                return super.visit(node, data);
            }
        }
        return super.visit(node, data);
    }
}