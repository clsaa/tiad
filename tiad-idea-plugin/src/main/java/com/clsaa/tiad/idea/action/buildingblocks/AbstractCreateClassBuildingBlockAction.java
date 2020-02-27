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

package com.clsaa.tiad.idea.action.buildingblocks;

import com.clsaa.tiad.idea.i18n.TiadBundle;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.JavaCreateTemplateInPackageAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author clsaa
 */
@Slf4j
public abstract class AbstractCreateClassBuildingBlockAction extends JavaCreateTemplateInPackageAction<PsiClass> implements DumbAware {
    public AbstractCreateClassBuildingBlockAction() {
        super("", IdeBundle.message("action.create.new.class.description"), PlatformIcons.CLASS_ICON, true);
    }

    @Override
    protected void buildDialog(final Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle(TiadBundle.message(this.getTitleKey()))
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
    protected String removeExtension(String templateName, String className) {
        return StringUtil.trimEnd(className, ".java");
    }

    @NotNull
    @Override
    protected String getErrorTitle() {
        return IdeBundle.message("title.cannot.create.class");
    }


    @Override
    protected String getActionName(PsiDirectory directory, @NotNull String newName, String templateName) {
        return IdeBundle.message("progress.creating.class", StringUtil.getQualifiedName(JavaDirectoryService.getInstance().getPackage(directory).getQualifiedName(), newName));
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    protected PsiClass doCreate(PsiDirectory dir, String className, String templateName) throws IncorrectOperationException {
        final Map<String, String> properties = new HashMap<>(4);
        properties.put("TIAD_IMPORT_PACKAGES", this.getImportPackages());
        properties.put("TIAD_ANNOTATIONS", this.getAnnotations());
        return JavaDirectoryService.getInstance().createClass(dir, className, templateName, true, properties);
    }

    @Override
    protected PsiElement getNavigationElement(@NotNull PsiClass createdElement) {
        return createdElement.getLBrace();
    }

    @Override
    protected void postProcess(PsiClass createdElement, String templateName, Map<String, String> customProperties) {
        super.postProcess(createdElement, templateName, customProperties);

        moveCaretAfterNameIdentifier(createdElement);
    }

    abstract public Class getBuildingBlockClass();


    public String getKind() {
        return this.getBuildingBlockClass().getSimpleName();
    }

    public Icon getIcon() {
        return PlatformIcons.CLASS_ICON;
    }

    public String getTemplateName() {
        return this.getBuildingBlockClass().getSimpleName();
    }

    public String getTitleKey() {
        return "action.create.new." + this.getBuildingBlockClass().getSimpleName().toLowerCase();
    }

    public String getImportPackages() {
        return "import " + this.getBuildingBlockClass().getName() + ";";
    }

    public String getAnnotations() {
        return "@ " + this.getBuildingBlockClass().getName();
    }
}
