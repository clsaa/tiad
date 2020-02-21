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

package com.clsaa.tiad.pmd.lang.java.rule.valueobject;

import com.clsaa.tiad.pmd.lang.java.rule.valueobject.testfile.ValueObjectNonIdentifierRuleTestTarget2;
import junit.framework.TestCase;
import net.sourceforge.pmd.RuleViolation;
import org.junit.Test;

import java.util.List;

public class ValueObjectNonIdentifierRuleTest2 extends AbstractValueObjectPmdTest {
    @Override
    public String getCheckFilePath() {
        return testfilePath + ValueObjectNonIdentifierRuleTestTarget2.class.getSimpleName() + ".java";
    }

    @Override
    public String getRuleName() {
        return ValueObjectNonIdentifierRule.class.getSimpleName();
    }

    @Test
    public void test() {
        final List<RuleViolation> ruleViolations = getRuleViolations();
        TestCase.assertEquals(1, ruleViolations.size());
    }
}