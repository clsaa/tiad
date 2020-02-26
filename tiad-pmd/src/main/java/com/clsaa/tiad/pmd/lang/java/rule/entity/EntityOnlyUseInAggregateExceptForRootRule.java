package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.AggregateRoot;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Entity;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.BuildBlockUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTImportDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.typeresolution.typedefinition.JavaTypeDefinition;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author clsaa
 */
@Slf4j
public class EntityOnlyUseInAggregateExceptForRootRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTImportDeclaration node, Object data) {
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final List<Entity> entities = buildBlockStructure.getByClass(Entity.class);
        if (entities.isEmpty()) {
            return super.visit(node, data);
        }
        final Set<String> entitySet = entities.stream().map(e -> e.getLocation().getFullClassName()).collect(Collectors.toSet());
        final Set<String> entityPackageSet = entities.stream().map(e -> e.getLocation().getPackageName()).collect(Collectors.toSet());
        final List<AggregateRoot> aggregateRoots = buildBlockStructure.getByClass(AggregateRoot.class);
        for (AggregateRoot aggregateRoot : aggregateRoots) {
            final Location location = aggregateRoot.getLocation();
            entitySet.remove(location.getFullClassName());
        }
        final String importedName = node.getImportedName();


        if (entitySet.contains(importedName) || entityPackageSet.contains(importedName)) {
            final String entityPackageName = node.getPackageName();
            final Aggregate entityAggregate = BuildBlockUtils.findParent(buildBlockStructure, Aggregate.class, entityPackageName);
            final ASTPackageDeclaration declaration = ASTUtils.getPackageDeclaration(node);
            final String currentPackageName = Objects.requireNonNull(declaration).getPackageNameImage();
            final Aggregate currentAggregate = BuildBlockUtils.findParent(buildBlockStructure, Aggregate.class, currentPackageName);
            if (currentAggregate == null || !currentAggregate.equals(entityAggregate)) {
                ViolationUtils.addViolationWithPrecisePosition(this, node, data);
            }
        }

        return super.visit(node, data);
    }


    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        final List<ASTType> types = node.findDescendantsOfType(ASTType.class);
        if (types.isEmpty()) {
            return super.visit(node, data);
        }

        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final List<Entity> entities = buildBlockStructure.getByClass(Entity.class);
        if (entities.isEmpty()) {
            return super.visit(node, data);
        }
        final Set<String> entitySet = entities.stream().map(e -> e.getLocation().getFullClassName()).collect(Collectors.toSet());
        final List<AggregateRoot> aggregateRoots = buildBlockStructure.getByClass(AggregateRoot.class);
        for (AggregateRoot aggregateRoot : aggregateRoots) {
            final Location location = aggregateRoot.getLocation();
            entitySet.remove(location.getFullClassName());
        }
        for (ASTType type : types) {
            final JavaTypeDefinition typeDefinition = type.getTypeDefinition();
            if (typeDefinition != null && entitySet.contains(typeDefinition.getType().getName())) {
                ViolationUtils.addViolationWithPrecisePosition(this, type, data);
            }
        }
        return super.visit(node, data);
    }
}