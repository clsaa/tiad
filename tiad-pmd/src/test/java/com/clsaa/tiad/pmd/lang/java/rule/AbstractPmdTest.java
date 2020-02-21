/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.pmd.lang.java.rule;

import net.sourceforge.pmd.*;
import org.junit.Before;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPmdTest {
    public PMDConfiguration configuration;
    public RuleSetFactory ruleSetFactory;
    public RuleSet ruleSet;
    public RuleContext ctx;
    public RuleSets ruleSets;
    public SourceCodeProcessor sourceCodeProcessor;
    public Report report;

    abstract public String getCheckFilePath();

    abstract public String getRuleSetName();

    abstract public String getRuleName();

    @Before
    public void before() throws Exception {
        this.configuration = new PMDConfiguration();
        this.ruleSetFactory = new RuleSetFactory();
        this.ruleSet = ruleSetFactory.createRuleSet(getRuleSetName().replace("/", "-"));
        this.ctx = getRuleContext();
        this.sourceCodeProcessor = new SourceCodeProcessor(configuration);
        this.report = Report.createReport(ctx, getCheckFilePath());

        for (Rule rule : ruleSet.getRules()) {
            if (rule.getName().equals(getRuleName())) {
                RuleSet singleRuleRuleSet = ruleSetFactory.createSingleRuleRuleSet(rule);
                this.ruleSets = new RuleSets(singleRuleRuleSet);
                final Reader read = read(getCheckFilePath());
                sourceCodeProcessor.processSourceCode(read, this.ruleSets, ctx);
            }
        }
    }

    public RuleContext getRuleContext() {
        RuleContext ruleContext = new RuleContext();
        return ruleContext;
    }

    public List<RuleViolation> getRuleViolations() {
        List<RuleViolation> result = new ArrayList<>();
        for (RuleViolation violation : report) {
            result.add(violation);
        }
        return result;
    }


    private static Reader read(String filePath) throws Exception {
        File file = new File(filePath);

        FileReader fr = new FileReader(file);
        char[] buff = new char[50];
        StringBuilder stringBuilder = new StringBuilder();
        int length;
        while ((length = fr.read(buff)) != -1) {
            stringBuilder.append(buff, 0, length);
        }
        fr.close();
        StringReader stringReader = new StringReader(stringBuilder.toString());
        return stringReader;
    }
}
