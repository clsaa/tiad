package com.clsaa.tiad.pmd.lang.java.processor;

import com.clsaa.tiad.pmd.DataKeys;
import com.clsaa.tiad.pmd.lang.java.context.TiadRuleContext;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.*;

/**
 * @author clsaa
 */
@Slf4j
public class TiadPmdProcessor {
    private static PMDConfiguration configuration = new PMDConfiguration();
    private static RuleSetFactory ruleSetFactory = new RuleSetFactory();

    public Report process(TiadRuleContext context) {

        RuleContext ruleContext = new RuleContext();
        ruleContext.setAttribute(DataKeys.BUILDING_BLOCK_STRUCTURE_DATA_KEY.getName(), context.getBuildingBlockStructure());

        final RuleSet ruleSet = ruleSetFactory.createSingleRuleRuleSet(context.getRule());
        final RuleSets ruleSets = new RuleSets(ruleSet);

        Report report = Report.createReport(ruleContext, context.getCurrentFileName());

        SourceCodeProcessor processor = new SourceCodeProcessor(configuration);
        try {
            processor.processSourceCode(context.getSourceCode(), ruleSets, ruleContext);
        } catch (PMDException e) {
            log.error("process failed, context:{}, ", context, e);
        }

        return report;
    }
}
