package com.clsaa.tiad.pmd.lang.java.rule;

import com.clsaa.tiad.pmd.I18nResources;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public abstract class AbstractTiadRule extends AbstractJavaRule {

    @Override
    public void setDescription(String description) {
        super.setDescription(I18nResources.getMessage(description));
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(I18nResources.getMessageWithExceptionHandled(message));
    }

    @Override
    public void addExample(String example) {
        super.addExample(I18nResources.getMessageWithExceptionHandled(example));
    }

    @Override
    public void addViolationWithMessage(Object data, Node node, String message) {
        super.addViolationWithMessage(data, node, I18nResources.getMessageWithExceptionHandled(message));
    }

    @Override
    public void addViolationWithMessage(Object data, Node node, String message, Object[] args) {
        super.addViolationWithMessage(data, node, String.format(I18nResources.getMessageWithExceptionHandled(message), args));
    }
}
