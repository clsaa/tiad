package com.clsaa.tiad.idea.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author clsaa
 */
@Getter
public abstract class TiadQuickFix implements LocalQuickFix {
    private static Map<String, TiadQuickFix> ruleNameIndex = new ConcurrentHashMap<>();

    private String ruleName;
    private Boolean onlyOnThFly;

    public static LocalQuickFix getQuickFix(String ruleName, Boolean isOnTheFly) {
        TiadQuickFix quickFix = ruleNameIndex.get(ruleName);
        if (quickFix == null) {
            return null;
        }
        if (!quickFix.getOnlyOnThFly()) {
            return quickFix;
        }
        if (!isOnTheFly && quickFix.onlyOnThFly) {
            return null;
        }
        return quickFix;
    }
}
