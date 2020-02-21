package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.common.data.DataKey;
import com.clsaa.tiad.pmd.DataKeys;
import com.clsaa.tiad.pmd.lang.java.constances.DefaultStrings;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class EntityShouldInAggregateRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final ASTAnnotation entityAnnotation = ASTUtils.findFirstAnnotation(node, Entity.class);
        if (entityAnnotation == null) {
            return super.visit(node, data);
        }
        final List<String> packageList = node.getQualifiedName().getPackageList();
        if (packageList.isEmpty()) {
            ViolationUtils.addViolationWithPrecisePosition(this, entityAnnotation, data);
        }
        final String qualifiedName = node.getQualifiedName().toString();
        final int lastIndexOfDot = qualifiedName.lastIndexOf(DefaultStrings.DOT);
        final String packageName = qualifiedName.substring(0, lastIndexOfDot);

        final RuleContext ruleContext = (RuleContext) data;
        final DataKey<BuildingBlockStructure> dataKey = DataKeys.BUILDING_BLOCK_STRUCTURE_DATA_KEY;
        final Object attribute = ruleContext.getAttribute(dataKey.getName());
        if (attribute == null) {
            return super.visit(node, data);
        }
        final BuildingBlockStructure buildingBlockStructure = dataKey.cast(attribute);
        final List<Aggregate> aggregates = buildingBlockStructure.getByClass(Aggregate.class);

        boolean inAggregate = false;
        for (Aggregate aggregate : aggregates) {
            final String aggregatePackage = aggregate.getLocation().getPackageName();
            if (StringUtils.isNoneBlank(aggregatePackage) && packageName.contains(aggregatePackage)) {
                inAggregate = true;
            }
        }
        if (!inAggregate) {
            ViolationUtils.addViolationWithPrecisePosition(this, entityAnnotation, data);
        }
        return super.visit(node, data);
    }
}