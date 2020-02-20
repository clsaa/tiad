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

package com.clsaa.tiad.idea.constances;

import com.google.common.collect.ImmutableMap;
import com.intellij.codeHighlighting.HighlightDisplayLevel;
import net.sourceforge.pmd.RulePriority;

import java.util.Map;

/**
 * @author clsaa
 */
public class RulePriorityHighlightLevelMappings {
    private static Map<RulePriority, HighlightDisplayLevel> MAPPINGS;

    static {
        MAPPINGS = ImmutableMap.of(
                RulePriority.HIGH, HighlightDisplayLevels.ERROR,
                RulePriority.MEDIUM_HIGH, HighlightDisplayLevels.WARNING,
                RulePriority.MEDIUM, HighlightDisplayLevels.WARNING,
                RulePriority.MEDIUM_LOW, HighlightDisplayLevels.SUGGESTION,
                RulePriority.LOW, HighlightDisplayLevels.SUGGESTION
        );
    }

    public static HighlightDisplayLevel getLevel(RulePriority rulePriority) {
        final HighlightDisplayLevel highlightDisplayLevel = MAPPINGS.get(rulePriority);
        if (highlightDisplayLevel == null) {
            return HighlightDisplayLevels.SUGGESTION;
        } else {
            return highlightDisplayLevel;
        }
    }

}
