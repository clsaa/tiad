package com.clsaa.tiad.pmd.lang.java.rule.boundedcontext;

import com.clsaa.tiad.buidlingblock.annotation.BoundedContext;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Cvs;
import com.clsaa.tiad.buidlingblock.entity.structure.ProjectDescriptor;
import com.clsaa.tiad.common.data.DataKey;
import com.clsaa.tiad.pmd.DataKeys;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import com.clsaa.tiad.pmd.lang.java.util.ASTUtils;
import com.clsaa.tiad.pmd.lang.java.util.ViolationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.java.ast.ASTAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;

import java.util.Map;

/**
 * @author clsaa
 */
@Slf4j
public class BoundedContextOnlyOneCodeRepositoryRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTPackageDeclaration node, Object data) {
        final ASTAnnotation bcAnnotation = ASTUtils.findFirstDescendantsAnnotation(node, BoundedContext.class);
        if (bcAnnotation == null) {
            return super.visit(node, data);
        }
        final RuleContext ruleContext = (RuleContext) data;
        final DataKey<ProjectDescriptor> dataKey = DataKeys.PROJECT_DESCRIPTOR_DATA_KEY;
        final Object attribute = ruleContext.getAttribute(dataKey.getName());
        if (attribute == null) {
            return super.visit(node, data);
        }
        final ProjectDescriptor projectDescriptor = dataKey.cast(attribute);
        final Map<String, Cvs> idCsvIndex = projectDescriptor.getIdCsvIndex();
        if (idCsvIndex != null && idCsvIndex.size() > 1) {
            ViolationUtils.addViolationWithPrecisePosition(this, bcAnnotation, data);
        }
        return super.visit(node, data);
    }
}