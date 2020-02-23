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

package com.clsaa.tiad.pmd.lang.java.rule.aggregate;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.common.data.MapDataContext;
import com.clsaa.tiad.pmd.DataKeys;
import com.clsaa.tiad.pmd.lang.java.rule.aggregate.testfile.AggregateShouldHaveOneRootRuleTestTarget;
import junit.framework.TestCase;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleViolation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AggregateBounderNonOverlapRuleTest extends AbstractAggregatePmdTest {
    @Override
    public String getCheckFilePath() {
        return testfilePath + "aggregate/" + "package-info" + ".java";
    }

    @Override
    public String getRuleName() {
        return AggregateShouldHaveOneRootRule.class.getSimpleName();
    }

    @Test
    public void test() {
        final List<RuleViolation> ruleViolations = getRuleViolations();
        TestCase.assertEquals(1, ruleViolations.size());
    }

    @Override
    public RuleContext getRuleContext() {
        final RuleContext ruleContext = new RuleContext();
        final BuildingBlockStructure buildingBlockStructure = new BuildingBlockStructure();
        MapDataContext dataContext = new MapDataContext();
        dataContext.putData(BuildingBlockDataKeys.RequiredToBuild.FILE_ID_DATA_KEY, "1");
        final Location location = Location.builder().packageName(AggregateShouldHaveOneRootRuleTestTarget.class.getPackage().getName()).build();
        dataContext.putData(BuildingBlockDataKeys.RequiredToBuild.LOCATION_DATA_KEY, location);
        UserSpecification userSpecification = UserSpecification.builder().code("1").name("1").description("1").build();
        dataContext.putData(BuildingBlockDataKeys.RequiredToBuild.USER_SPECIFICATION_DATA_KEY, userSpecification);
        Aggregate aggregate = new Aggregate(dataContext);
        buildingBlockStructure.put(aggregate);
        ruleContext.setAttribute(DataKeys.BUILDING_BLOCK_STRUCTURE_DATA_KEY.getName(), buildingBlockStructure);
        return ruleContext;
    }
}