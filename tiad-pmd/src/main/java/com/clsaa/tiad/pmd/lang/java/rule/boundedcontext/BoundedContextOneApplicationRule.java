package com.clsaa.tiad.pmd.lang.java.rule.boundedcontext;

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
public class BoundedContextOneApplicationRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTPackageDeclaration node, Object data) {
        final ASTAnnotation bcAnnotation = ASTUtils.findFirstDescendantsAnnotation(node, com.clsaa.tiad.buidlingblock.annotation.BoundedContext.class);
        if (bcAnnotation == null) {
            return super.visit(node, data);
        }
        final BuildingBlockStructure buildBlockStructure = BuildBlockUtils.getBuildBlockStructure(data);
        if (buildBlockStructure == null) {
            return super.visit(node, data);
        }
        final List<BoundedContext> boundedContexts = buildBlockStructure.getByClass(BoundedContext.class);
        if (boundedContexts.size() != 1) {
            ViolationUtils.addViolationWithPrecisePosition(this, bcAnnotation, data);
        }
        return super.visit(node, data);
    }
}