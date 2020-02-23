package com.clsaa.tiad.pmd.lang.java.rule.aggregate;

import com.clsaa.tiad.buidlingblock.annotation.Aggregate;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.AggregateRoot;
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
public class AggregateShouldHaveOneRootRule extends AbstractTiadRule {
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
        final List<AggregateRoot> aggregateRoots = buildBlockStructure.getByClass(AggregateRoot.class);
        if (aggregateRoots.isEmpty()) {
            ViolationUtils.addViolationWithPrecisePosition(this, aggregateAnnotation, data);
            return super.visit(node, data);
        }
        final String aggregateBounder = node.getPackageNameImage();
        int rootCount = 0;
        for (AggregateRoot aggregateRoot : aggregateRoots) {
            final String packageName = aggregateRoot.getLocation().getPackageName();
            if (packageName.contains(aggregateBounder)) {
                rootCount++;
            }
        }
        if (rootCount != 1) {
            ViolationUtils.addViolationWithPrecisePosition(this, aggregateAnnotation, data);
        }
        return super.visit(node, data);
    }
}