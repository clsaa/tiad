package com.clsaa.tiad.idea.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author clsaa
 */
@Getter
@Slf4j
public abstract class TiadQuickFix implements LocalQuickFix {
    private static Map<String, TiadQuickFix> ruleNameIndex = new ConcurrentHashMap<>();

    static {
        Reflections reflections = new Reflections(TiadQuickFix.class.getPackage().getName());
        Set<Class<? extends TiadQuickFix>> subTypesOf = reflections.getSubTypesOf(TiadQuickFix.class);
        for (Class<? extends TiadQuickFix> aClass : subTypesOf) {
            try {
                if (Modifier.isAbstract(aClass.getModifiers()) || Modifier.isInterface(aClass.getModifiers())) {
                    continue;
                }
                aClass.newInstance();
            } catch (Exception e) {
                log.error("BuilderChain failed, ", e);
                throw new RuntimeException(e);
            }
        }
    }

    private String ruleName;
    private Boolean onlyOnThFly;

    public TiadQuickFix() {
        this.ruleName = this.getRuleName();
        this.onlyOnThFly = this.getOnlyOnThFly();
        ruleNameIndex.put(ruleName, this);
    }

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

    public abstract String getRuleName();

    public abstract Boolean getOnlyOnThFly();
}
