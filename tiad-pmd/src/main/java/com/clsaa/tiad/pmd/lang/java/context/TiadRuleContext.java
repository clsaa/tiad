package com.clsaa.tiad.pmd.lang.java.context;

import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.buidlingblock.entity.structure.ProjectDescriptor;
import lombok.Builder;
import lombok.Data;
import net.sourceforge.pmd.Rule;

import java.io.Reader;

/**
 * @author clsaa
 */
@Data
@Builder
public class TiadRuleContext {
    private ProjectDescriptor projectDescriptor;
    private BuildingBlockStructure buildingBlockStructure;
    private String currentFileName;
    private Reader sourceCode;
    private Rule rule;
}
