/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.idea.inspection;

import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.clsaa.tiad.pmd.lang.java.context.TiadRuleContext;
import com.clsaa.tiad.pmd.lang.java.processor.TiadPmdProcessor;
import com.google.common.base.Throwables;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.application.ex.ApplicationUtil;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class TiadPmdInspectionInvoker {

    private TiadPmdInspectionInvoker() {
    }

    public ProblemDescriptor[] invoke(TiadPmdInspectionContext context) {
        final PsiFile psiFile = context.getPsiFile();
        final VirtualFile virtualFile = psiFile.getVirtualFile();
        final Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        final InspectionManager manager = context.getManager();
        final Project project = manager.getProject();

        final BuildingBlockStructureService buildingBlockStructureService = BuildingBlockStructureService.getInstance(project);
        buildingBlockStructureService.update(psiFile);
        final BuildingBlockStructure buildingBlockStructure = buildingBlockStructureService.get();

        final String nickFileName = virtualFile.getCanonicalPath();

        final TiadRuleContext tiadRuleContext = TiadRuleContext.builder().sourceCode(new StringReader(document.getText()))
                .currentFileName(nickFileName).rule(context.getRule()).buildingBlockStructure(buildingBlockStructure).build();

        try {
            Report report = new TiadPmdProcessor().process(tiadRuleContext);
            List<RuleViolation> violations = new ArrayList<>();
            for (RuleViolation violation : report) {
                violations.add(violation);
            }
            context.setViolations(violations);
        } catch (RuntimeException e) {
            Throwable root = Throwables.getRootCause(e);
            if (!(root instanceof ApplicationUtil.CannotRunReadActionException)) {
                log.error("RuntimeException while processing file: {}", nickFileName, e);
            }
        }


        final ProblemDescriptor[] problemDescriptors = TiadPmdViolationParser.getInstance().parser(context);
        return problemDescriptors;
    }

    public static TiadPmdInspectionInvoker getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static TiadPmdInspectionInvoker instance = new TiadPmdInspectionInvoker();
    }
}
