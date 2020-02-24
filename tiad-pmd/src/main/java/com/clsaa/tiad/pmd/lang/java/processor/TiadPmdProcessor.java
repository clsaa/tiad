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
        configuration.setClassLoader(Thread.currentThread().getContextClassLoader());
        RuleContext ruleContext = new RuleContext();
        ruleContext.setAttribute(DataKeys.BUILDING_BLOCK_STRUCTURE_DATA_KEY.getName(), context.getBuildingBlockStructure());
        ruleContext.setAttribute(DataKeys.PROJECT_DESCRIPTOR_DATA_KEY.getName(), context.getProjectDescriptor());

        final RuleSet ruleSet = ruleSetFactory.createSingleRuleRuleSet(context.getRule());
        final RuleSets ruleSets = new RuleSets(ruleSet);

        final String currentFileName = context.getCurrentFileName();
        Report report = Report.createReport(ruleContext, currentFileName);

        SourceCodeProcessor processor = new SourceCodeProcessor(configuration);
        try {
            processor.processSourceCode(context.getSourceCode(), ruleSets, ruleContext);
        } catch (PMDException e) {
            log.debug("Error while processing file: {}", currentFileName, e);
            report.addError(new Report.ProcessingError(e, currentFileName));
        } catch (Exception e) {
            log.error("process failed, context:{}, ", context, e);
            throw new RuntimeException(e);
        }

        return report;
    }
}
