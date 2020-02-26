package com.clsaa.tiad.pmd.lang.java.rule;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.BuildBlockUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;

import java.util.List;
import java.util.Objects;

/**
 * @author clsaa
 */
@Slf4j
public abstract class AbstractOneToOneRule extends AbstractAnnotatableRule {

    public abstract Class<? extends BuildingBlock> getSubBuildingBlockClass();

    public abstract Class<? extends BuildingBlock> getParentBuildingBlockClass();

    @Override
    public Object visit(ASTAnnotation node, Object data) {
        final ASTAnnotation annotation = ASTUtils.findFirstDescendantsAnnotation(node, this.getTargetAnnotation());
        if (annotation == null) {
            return null;
        }
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return null;
        }
        final String packageNameImage = Objects.requireNonNull(ASTUtils.getPackageDeclaration(node)).getPackageNameImage();
        final List<? extends BuildingBlock> parentBuildingBlocks = buildBlockStructure.getByClass(getParentBuildingBlockClass());
        final List<? extends BuildingBlock> subBuildingBlocks = buildBlockStructure.getByClass(getSubBuildingBlockClass());
        for (BuildingBlock parent : parentBuildingBlocks) {
            final String bcPackage = parent.getLocation().getPackageName();
            if (packageNameImage.startsWith(bcPackage)) {
                for (BuildingBlock sub : subBuildingBlocks) {
                    final String subPackageName = sub.getLocation().getPackageName();
                    if (subPackageName.startsWith(bcPackage) && !subPackageName.equals(packageNameImage)) {
                        ViolationUtils.addViolationWithPrecisePosition(this, node, data);
                        return null;
                    }
                }
            }
        }
        return null;
    }

}