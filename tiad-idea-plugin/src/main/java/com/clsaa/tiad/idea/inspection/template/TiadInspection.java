package com.clsaa.tiad.idea.inspection.template;

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author clsaa
 */
public interface TiadInspection {
    String GROUP_NAME = "TIAD-CHECK";

    String getRuleName();

    String getDisplayName();

    default String getDisplayGroupName() {
        return GROUP_NAME;
    }

    default Boolean isDefaultEnable() {
        return true;
    }

    HighlightDisplayLevel getDefaultLevel();

    LocalQuickFix manualBuildFix(PsiElement psiElement, Boolean isOnTheFly);

    default PsiElement manualParsePsiElement(PsiFile psiFile, InspectionManager manager, Integer start, Integer end) {
        return psiFile.findElementAt(start);
    }
}
