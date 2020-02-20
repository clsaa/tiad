package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.pmd.lang.java.rule.AbstractTiadRule;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;

/**
 * @author clsaa
 */
@Slf4j
public class EntityMustHaveOneIdentifierRule extends AbstractTiadRule {
    @Override
    public Object visit(ASTMarkerAnnotation node, Object data) {
        log.info("visit node:{}, data:{} ", node, data);
        return super.visit(node, data);
    }
}