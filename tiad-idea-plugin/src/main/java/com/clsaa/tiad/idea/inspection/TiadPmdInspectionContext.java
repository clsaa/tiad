package com.clsaa.tiad.idea.inspection;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import lombok.Builder;
import lombok.Data;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleViolation;

import java.util.List;

/**
 * @author clsaa
 */
@Data
@Builder
public class TiadPmdInspectionContext {
    private PsiFile psiFile;
    private VirtualFile virtualFile;
    private InspectionManager manager;
    private Rule rule;
    private List<RuleViolation> violations;
    private boolean isOnTheFly;
}
