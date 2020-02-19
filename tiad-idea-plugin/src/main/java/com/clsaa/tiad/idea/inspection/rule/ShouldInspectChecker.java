package com.clsaa.tiad.idea.inspection.rule;

import com.intellij.psi.PsiFile;

@FunctionalInterface
public interface ShouldInspectChecker {
    ShouldInspectChecker TRUE = psiFile -> true;
    ShouldInspectChecker FALSE = psiFile -> false;

    boolean shouldInspect(PsiFile psiFile);
}
