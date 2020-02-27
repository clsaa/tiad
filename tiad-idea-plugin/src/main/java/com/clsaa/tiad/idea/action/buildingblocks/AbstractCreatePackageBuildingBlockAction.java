/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clsaa.tiad.idea.action.buildingblocks;

import com.clsaa.tiad.idea.i18n.TiadBundle;
import com.intellij.CommonBundle;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.IdeView;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.actions.AttributesDefaults;
import com.intellij.ide.fileTemplates.actions.CreateFromTemplateActionBase;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiNameHelper;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;

import javax.swing.*;

import static com.intellij.ide.fileTemplates.JavaTemplateUtil.INTERNAL_PACKAGE_INFO_TEMPLATE_NAME;

/**
 * @author Bas Leijdekkers
 */
public abstract class AbstractCreatePackageBuildingBlockAction extends CreateFromTemplateActionBase {
    public AbstractCreatePackageBuildingBlockAction() {
        super("package-info.java", "Create new package-info.java", PlatformIcons.PACKAGE_ICON);
        final Presentation templatePresentation = this.getTemplatePresentation();
        templatePresentation.setText(TiadBundle.message(this.getTitleKey()));
        templatePresentation.setDescription(TiadBundle.message(this.getDescriptionKey()));
        templatePresentation.setIcon(this.getIcon());
    }

    @Nullable
    @Override
    protected PsiDirectory getTargetDirectory(DataContext dataContext, IdeView view) {
        final PsiDirectory[] directories = view.getDirectories();
        for (PsiDirectory directory : directories) {
            final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(directory);
            if (aPackage == null) {
                continue;
            }
            if (directory.findFile(PsiPackage.PACKAGE_INFO_FILE) != null) {
                Messages.showErrorDialog(CommonDataKeys.PROJECT.getData(dataContext), "error.package.already.contains.package-info",
                        "title.cannot.create.file");
                return null;
            } else if (directory.findFile("package.html") != null) {
                if (Messages.showOkCancelDialog(CommonDataKeys.PROJECT.getData(dataContext),
                        "error.package.already.contains.package.html", "error.package.html.found.title",
                        IdeBundle.message("button.create"), CommonBundle.getCancelButtonText(),
                        Messages.getQuestionIcon()) != Messages.OK) {
                    return null;
                }
            }

        }
        return super.getTargetDirectory(dataContext, view);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(isAvailable(e.getDataContext()));
    }

    private static boolean isAvailable(DataContext dataContext) {
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (project == null || view == null) {
            return false;
        }
        final PsiDirectory[] directories = view.getDirectories();
        if (directories.length == 0) {
            return false;
        }
        final ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        final JavaDirectoryService directoryService = JavaDirectoryService.getInstance();
        final PsiNameHelper nameHelper = PsiNameHelper.getInstance(project);
        for (PsiDirectory directory : directories) {
            if (projectFileIndex.isUnderSourceRootOfType(directory.getVirtualFile(), JavaModuleSourceRootTypes.SOURCES) &&
                    PsiUtil.isLanguageLevel5OrHigher(directory)) {
                final PsiPackage aPackage = directoryService.getPackage(directory);
                if (aPackage != null) {
                    final String qualifiedName = aPackage.getQualifiedName();
                    if (StringUtil.isEmpty(qualifiedName) || nameHelper.isQualifiedName(qualifiedName)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Nullable
    @Override
    public AttributesDefaults getAttributesDefaults(DataContext dataContext) {
        final AttributesDefaults attributesDefaults = new AttributesDefaults(INTERNAL_PACKAGE_INFO_TEMPLATE_NAME).withFixedName(true);
        attributesDefaults.add("TIAD_IMPORT_PACKAGES", this.getImportPackages());
        attributesDefaults.add("TIAD_ANNOTATIONS", this.getAnnotations());
        return attributesDefaults;
    }

    @Override
    protected FileTemplate getTemplate(Project project, PsiDirectory dir) {
        return FileTemplateManager.getInstance(project).getInternalTemplate(this.getTemplateName());
    }


    abstract public Class getBuildingBlockClass();

    public String getTemplateName() {
        return this.getBuildingBlockClass().getSimpleName();
    }

    public String getKey() {
        return this.getBuildingBlockClass().getSimpleName().toLowerCase();
    }

    public String getTitleKey() {
        return "action.create.new." + this.getKey();
    }

    public String getDescriptionKey() {
        return "action.create.new." + this.getKey() + ".description";
    }

    public Icon getIcon() {
        return PlatformIcons.PACKAGE_ICON;
    }

    public String getImportPackages() {
        return "import " + this.getBuildingBlockClass().getName() + ";";
    }

    public String getAnnotations() {
        return "@ " + this.getBuildingBlockClass().getName();
    }
}