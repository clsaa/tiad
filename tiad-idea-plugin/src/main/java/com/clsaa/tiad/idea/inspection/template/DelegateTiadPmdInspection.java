package com.clsaa.tiad.idea.inspection.template;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class DelegateTiadPmdInspection extends LocalInspectionTool implements TiadInspection {
    /**
     * ruleName will be replaced when generate inspection with javassist
     */
    private String ruleName = "";
    private TiadPmdInspection realInspection;

    public DelegateTiadPmdInspection() {
        this.realInspection = new TiadPmdInspection(ruleName);
    }

    @Override
    public String getRuleName() {
        return this.realInspection.getRuleName();
    }

    @Override
    public String getDisplayName() {
        return this.realInspection.getDisplayName();
    }

    @Override
    public String getDisplayGroupName() {
        return this.realInspection.getDisplayGroupName();
    }

    @Override
    public Boolean isDefaultEnable() {
        return this.realInspection.isDefaultEnable();
    }

    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        return this.realInspection.checkFile(file, manager, isOnTheFly);
    }

    @NotNull
    @Override
    public HighlightDisplayLevel getDefaultLevel() {
        return this.realInspection.getDefaultLevel();
    }

    @Override
    public LocalQuickFix manualBuildFix(PsiElement psiElement, Boolean isOnTheFly) {
        return this.realInspection.manualBuildFix(psiElement, isOnTheFly);
    }
}
