package com.clsaa.tiad.idea.inspection;

import com.clsaa.tiad.idea.inspection.descriptor.Offsets;
import com.clsaa.tiad.idea.quickfix.TiadQuickFix;
import com.clsaa.tiad.idea.util.DocumentUtil;
import com.clsaa.tiad.idea.util.ProblemsUtils;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import kotlin.jvm.functions.Function1;
import net.sourceforge.pmd.RuleViolation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clsaa
 */
public class TiadPmdViolationParser {
    private TiadPmdViolationParser() {
    }

    public ProblemDescriptor[] parser(TiadPmdInspectionContext context) {
        final List<RuleViolation> violations = context.getViolations();
        final InspectionManager manager = context.getManager();
        final boolean isOnTheFly = context.isOnTheFly();
        if (violations.isEmpty()) {
            return null;
        }
        final ArrayList<ProblemDescriptor> problemDescriptors = new ArrayList<>(violations.size());

        for (RuleViolation violation : violations) {
            final String ruleName = violation.getRule().getName();
            final VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(violation.getFilename());
            if (virtualFile == null) {
                continue;
            }
            final PsiFile psiFile = PsiManager.getInstance(manager.getProject()).findFile(virtualFile);
            if (psiFile == null) {
                continue;
            }
            final Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
            if (document == null) {
                continue;
            }

            final int start = DocumentUtil.calculateRealOffset(document, violation.getBeginLine(), violation.getBeginColumn());
            final int offset = DocumentUtil.calculateRealOffset(document, violation.getEndLine(), violation.getEndColumn());
            final Offsets offsets = new Offsets(start, offset);

            String errorMsg;
            if (isOnTheFly) {
                errorMsg = violation.getDescription();
            } else {
                errorMsg = violation.getDescription() + " (line " + violation.getBeginLine() + ")";
            }
            ProblemDescriptor problemDescriptor = ProblemsUtils.INSTANCE.createProblemDescriptorForPmdRule(psiFile, manager,
                    isOnTheFly, ruleName, errorMsg, offsets.getStart(), offsets.getEnd(), violation.getBeginLine(), new Function1<PsiElement, LocalQuickFix>() {
                        @Override
                        public LocalQuickFix invoke(PsiElement psiElement) {
                            return TiadQuickFix.getQuickFix(ruleName, isOnTheFly);
                        }
                    });
            problemDescriptors.add(problemDescriptor);
        }
        return problemDescriptors.toArray(new ProblemDescriptor[0]);
    }

    public static TiadPmdViolationParser getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static TiadPmdViolationParser instance = new TiadPmdViolationParser();
    }


}
