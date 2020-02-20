package com.clsaa.tiad.idea.inspection;

import com.clsaa.tiad.idea.config.TiadConfig;
import com.clsaa.tiad.idea.inspection.rule.BuiltinRuleSetNames;
import com.clsaa.tiad.idea.inspection.rule.JavaShouldInspectChecker;
import com.clsaa.tiad.idea.inspection.rule.RuleSpecification;
import com.clsaa.tiad.idea.inspection.template.DelegateTiadPmdInspection;
import com.clsaa.tiad.pmd.I18nResources;
import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.openapi.components.ServiceManager;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author clsaa
 */
@Slf4j
@Data
public class TiadPmdInspectionProvider implements InspectionToolProvider {
    private static TiadPmdInspectionProvider self;
    List<Class<? extends LocalInspectionTool>> localInspectionTools = new ArrayList<>(64);
    Map<String, RuleSpecification> ruleNameIndex = new HashMap<>(64);

    @NotNull
    @Override
    public Class<? extends LocalInspectionTool>[] getInspectionClasses() {
        return localInspectionTools.toArray(new Class[0]);
    }

    public TiadPmdInspectionProvider() {
        final TiadConfig config = ServiceManager.getService(TiadConfig.class);
        I18nResources.changeLanguage(config.getLocale());
        Thread.currentThread().setContextClassLoader(TiadPmdInspectionProvider.class.getClassLoader());

        final RuleSet ruleSet = RuleSetLoader.loadBuiltinRuleSet(BuiltinRuleSetNames.TIAD_BUILTIN_RULE_SET_NAME_JAVA);

        for (Rule rule : ruleSet.getRules()) {
            RuleSpecification ruleSpecification = RuleSpecification.builder()
                    .rule(rule).shouldInspectChecker(JavaShouldInspectChecker.getInstance()).build();
            final String name = rule.getName();
            ruleNameIndex.put(name, ruleSpecification);
        }

        final ClassPool classPool = ClassPool.getDefault();
        final ClassClassPath classPath = new ClassClassPath(DelegateTiadPmdInspection.class);
        classPool.insertClassPath(classPath);
        try {
            for (RuleSpecification ruleSpecification : ruleNameIndex.values()) {
                final CtClass cc = classPool.get(DelegateTiadPmdInspection.class.getName());
                cc.setName(ruleSpecification.getRule().getName() + "Inspection");
                String value = "\"" + ruleSpecification.getRule().getName() + "\"";
                final CtConstructor[] constructors = cc.getConstructors();
                for (CtConstructor constructor : constructors) {
                    cc.removeConstructor(constructor);
                }
                CtConstructor cons = new CtConstructor(new CtClass[]{}, cc);
                cons.setBody("{$0.realInspection = new com.clsaa.tiad.idea.inspection.template.TiadPmdInspection(" + value + ");}");
                cc.addConstructor(cons);
                localInspectionTools.add((Class<? extends LocalInspectionTool>) cc.toClass());
            }
        } catch (Exception e) {
            log.error("TiadPmdInspectionProvider failed, ", e);
        }
        self = this;
    }

    public static TiadPmdInspectionProvider getSelf() {
        return self;
    }
}
