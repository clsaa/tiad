package com.clsaa.tiad.idea.inspection;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

/**
 * @author clsaa
 */
public class PmdInspectionProvider implements InspectionToolProvider {
    @NotNull
    @Override
    public Class<? extends LocalInspectionTool>[] getInspectionClasses() {
        return new Class[0];
    }
}
