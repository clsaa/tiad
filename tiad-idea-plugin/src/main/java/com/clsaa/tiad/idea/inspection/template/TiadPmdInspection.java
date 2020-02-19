package com.clsaa.tiad.idea.inspection.template;

import com.clsaa.tiad.idea.constances.NumberConstants;
import com.clsaa.tiad.idea.inspection.TiadPmdInspectionContext;
import com.clsaa.tiad.idea.inspection.TiadPmdInspectionInvoker;
import com.clsaa.tiad.idea.inspection.util.RuleInspectionUtils;
import com.clsaa.tiad.idea.inspection.TiadPmdInspectionProvider;
import com.clsaa.tiad.idea.inspection.rule.RuleSpecification;
import com.clsaa.tiad.idea.inspection.rule.ShouldInspectChecker;
import com.clsaa.tiad.idea.quickfix.TiadQuickFix;
import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import net.sourceforge.pmd.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author clsaa
 */
public class TiadPmdInspection extends LocalInspectionTool implements TiadInspection {
    private String ruleName;
    private Rule rule;
    private String displayName;
    private String staticDescription;
    private ShouldInspectChecker shouldInspectChecker;
    private HighlightDisplayLevel defaultLevel;

    public TiadPmdInspection(String ruleName) {
        this.ruleName = ruleName;
        final TiadPmdInspectionProvider tiadPmdInspectionProvider = TiadPmdInspectionProvider.getSelf();
        assert tiadPmdInspectionProvider != null;
        final RuleSpecification ruleSpecification = tiadPmdInspectionProvider.getRuleNameIndex().get(ruleName);
        assert ruleSpecification != null;
        this.rule = ruleSpecification.getRule();
        this.shouldInspectChecker = ruleSpecification.getShouldInspectChecker();
        this.displayName = rule.getMessage();
        this.staticDescription = RuleInspectionUtils.INSTANCE.getRuleStaticDescription(ruleName);
        this.defaultLevel = RuleInspectionUtils.INSTANCE.getHighlightDisplayLevel(ruleName);
    }

    @Override
    public String getRuleName() {
        return this.ruleName;
    }

    @Override
    public LocalQuickFix manualBuildFix(PsiElement psiElement, Boolean isOnTheFly) {
        return TiadQuickFix.getQuickFix(this.ruleName, isOnTheFly);

    }


    /**
     * Override LocalInspectionTool
     */
    @Override
    public boolean runForWholeFile() {
        return true;
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {

        if (!shouldInspectChecker.shouldInspect(file)) {
            return null;
        }
        final VirtualFile virtualFile = file.getVirtualFile();
        if (virtualFile == null) {
            return null;
        }
        TiadPmdInspectionContext context = TiadPmdInspectionContext.builder()
                .isOnTheFly(isOnTheFly).psiFile(file).virtualFile(virtualFile).rule(rule).manager(manager).build();
        return TiadPmdInspectionInvoker.getInstance().invoke(context);
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return this.staticDescription;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @NotNull
    @Override
    public HighlightDisplayLevel getDefaultLevel() {
        return this.defaultLevel;
    }

    @NotNull
    @Override
    public String getGroupDisplayName() {
        return TiadInspection.GROUP_NAME;
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Override
    public boolean isSuppressedFor(@NotNull PsiElement element) {
        return false;
    }

    @NotNull
    @Override
    public String getShortName() {
        String shortName = "Tiad" + ruleName;
        int index = shortName.lastIndexOf("Rule");
        if (index > NumberConstants.INDEX_0) {
            shortName = shortName.substring(NumberConstants.INDEX_0, index);
        }
        return shortName;
    }
}
