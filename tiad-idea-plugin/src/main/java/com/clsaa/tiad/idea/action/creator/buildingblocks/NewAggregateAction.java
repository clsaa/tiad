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

package com.clsaa.tiad.idea.action.creator.buildingblocks;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
import com.clsaa.tiad.idea.action.creator.java.TiadCreateClassAction;
import com.clsaa.tiad.idea.constances.Icons;
import com.clsaa.tiad.idea.i18n.TiadBundle;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiNameHelper;
import com.intellij.util.IncorrectOperationException;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * @author clsaa
 */
@Slf4j
public class NewAggregateAction extends TiadCreateClassAction {
    @Override
    protected void buildDialog(final Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle(TiadBundle.INSTANCE.message(this.getTitleKey()))
                .addKind(this.getKind(), this.getIcon(), this.getTemplateName());


        builder.setValidator(new InputValidatorEx() {
            @Override
            public String getErrorText(String inputString) {
                if (inputString.length() > 0 && !PsiNameHelper.getInstance(project).isQualifiedName(inputString)) {
                    return "This is not a valid Java qualified name";
                }
                return null;
            }

            @Override
            public boolean checkInput(String inputString) {
                return true;
            }

            @Override
            public boolean canClose(String inputString) {
                return !StringUtil.isEmptyOrSpaces(inputString) && getErrorText(inputString) == null;
            }
        });
    }

    @Override
    protected PsiClass doCreate(PsiDirectory dir, String className, String templateName) throws IncorrectOperationException {
        return JavaDirectoryService.getInstance().createClass(dir, className, templateName, true);
    }


    public String getKind() {
        return Aggregate.class.getSimpleName();
    }

    public Icon getIcon() {
        return Icons.AGGREGATE;
    }

    public String getTemplateName() {
        return Aggregate.class.getSimpleName();
    }

    public String getTitleKey() {
        return "action.create.new." + Aggregate.class.getSimpleName().toLowerCase();
    }

    public String getImportPackages() {
        return "import " + Aggregate.class.getName() + ";";
    }

    public String getAnnotations() {
        return "@ " + Aggregate.class.getSimpleName();
    }
}
