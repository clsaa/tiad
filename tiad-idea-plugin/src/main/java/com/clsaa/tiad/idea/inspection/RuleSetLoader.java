package com.clsaa.tiad.idea.inspection;

import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleSetNotFoundException;

/**
 * @author clsaa
 */
public class RuleSetLoader {

    static RuleSet loadBuiltinRuleSet(String builtinRuleSetName) {
        final RuleSetFactory factory = new RuleSetFactory();
        final String ruleSetName = builtinRuleSetName.replace("/", "-");
        try {
            final RuleSet ruleSet = factory.createRuleSet(ruleSetName);
            return ruleSet;
        } catch (RuleSetNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
