package com.clsaa.tiad.idea.inspection.rule;

import lombok.Builder;
import lombok.Data;
import net.sourceforge.pmd.Rule;

/**
 * @author clsaa
 */
@Data
@Builder
public class RuleSpecification {
    private Rule rule;
    private ShouldInspectChecker shouldInspectChecker;
}
