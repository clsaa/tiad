package com.clsaa.tiad.idea.inspection.template;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelegateTiadPmdInspection extends LocalInspectionTool implements TiadInspection {
    /**
     * ruleName will be replaced when generate inspection with javassist
     */
    private TiadPmdInspection realInspection;


    @Override
    public String getRuleName() {
        return this.realInspection.getRuleName();
    }

    @Override
    public LocalQuickFix manualBuildFix(PsiElement psiElement, Boolean isOnTheFly) {
        return this.realInspection.manualBuildFix(psiElement, isOnTheFly);
    }

    /**
     * Override LocalInspectionTool
     */
    @Override
    public boolean runForWholeFile() {
        return this.realInspection.runForWholeFile();
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        return this.realInspection.checkFile(file, manager, isOnTheFly);
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return this.realInspection.getStaticDescription();
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return this.realInspection.getDisplayName();
    }

    @NotNull
    @Override
    public HighlightDisplayLevel getDefaultLevel() {
        return this.realInspection.getDefaultLevel();
    }

    @NotNull
    @Override
    public String getGroupDisplayName() {
        return this.realInspection.getGroupDisplayName();
    }

    @Override
    public boolean isEnabledByDefault() {
        return this.realInspection.isEnabledByDefault();
    }

    @Override
    public boolean isSuppressedFor(@NotNull PsiElement element) {
        return this.realInspection.isSuppressedFor(element);
    }

    @NotNull
    @Override
    public String getShortName() {
        return this.realInspection.getShortName();
    }
}
